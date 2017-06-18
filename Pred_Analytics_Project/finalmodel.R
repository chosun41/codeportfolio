# Preprocessing

dondat<-read.csv("donation data.csv")
datdef<-read.csv("dmef1code.csv")

# create index from rownames of dondat
# merge dondat and datdef to get the corresponding type category for each contribution and solitication variable
# all.x=T will keep the original values from dondat table if no match so that nrow of data is same
# also rename the codetype variable for the corresponding variable it was created from
# reorder newdondat by index and then remove the index variable
dondat$INDEX<-as.numeric(rownames(dondat))
newdondat<-merge(dondat, datdef, by.x = "CNCOD1", by.y="CODE", all.x=T)
names(newdondat)[names(newdondat)=="CODETYPE"]<-"CNCOD1CAT"
newdondat<-merge(newdondat, datdef, by.x = "CNCOD2", by.y="CODE", all.x=T)
names(newdondat)[names(newdondat)=="CODETYPE"]<-"CNCOD2CAT"
newdondat<-merge(newdondat, datdef, by.x = "CNCOD3", by.y="CODE", all.x=T)
names(newdondat)[names(newdondat)=="CODETYPE"]<-"CNCOD3CAT"
newdondat<-merge(newdondat, datdef, by.x = "SLCOD1", by.y="CODE", all.x=T)
names(newdondat)[names(newdondat)=="CODETYPE"]<-"SLCOD1CAT"
newdondat<-merge(newdondat, datdef, by.x = "SLCOD2", by.y="CODE", all.x=T)
names(newdondat)[names(newdondat)=="CODETYPE"]<-"SLCOD2CAT"
newdondat<-merge(newdondat, datdef, by.x = "SLCOD3", by.y="CODE", all.x=T)
names(newdondat)[names(newdondat)=="CODETYPE"]<-"SLCOD3CAT"
newdondat<-newdondat[order(newdondat$INDEX),]
newdondat$INDEX<-NULL

# fill in na cndol values with 0 as we are assuming these are cases of no donations 
# and not that donations weren't collected
newdondat$CNDOL2[is.na(newdondat$CNDOL2)]<-0
newdondat$CNDOL3[is.na(newdondat$CNDOL3)]<-0

# create variable for avg cont per times contributed
newdondat$CONTPERTIME<-newdondat$CNTRLIF/newdondat$CNTMLIF

# create categorical variable for TARGDOL>0
newdondat$TARGDOLCAT<-ifelse(newdondat$TARGDOL>0,1,0)
# newdondat$TARGDOLCAT<-factor(newdondat$TARGDOLCAT)

head(newdondat)
# exclude original cod variables, keep codcat new variables, 
# get rid of cod2cat and cod3cat variables, mon dat 2,3 variables bc we can't impute these
# get rid of ID variable and state variable
finaldondat<-newdondat[ , -c(1,2,3,4,5,6,12,13,18,21,22,25,27,28,30,31)]
finaldondat$CNCOD1CAT<-factor(finaldondat$CNCOD1CAT)
finaldondat$SLCOD1CAT<-factor(finaldondat$SLCOD1CAT)
head(finaldondat)

# split into test and training data set
test_indices<-seq(3,nrow(finaldondat), by=3)
train<-finaldondat[-test_indices, ]
test<-finaldondat[test_indices, ]

#######################################################################################################

# EDA for only numeric variables and targdolcat==1

edamlrdondat<-finaldondat[finaldondat$TARGDOLCAT==1,-c(10,14,15,17)]

head(edamlrdondat)
pairs(edamlrdondat[1:13], gap=0, pch = 21)

R<-cor(edamlrdondat)
write.csv(R, "correlationmatrix.csv")

# EDA for categorical variables

boxplot(TARGDOL~SEX, data=finaldondat[finaldondat$TARGDOLCAT==1, ], ylim=c(0,300), xlab="SEX", ylab="TARGDOL")
boxplot(TARGDOL~CNCOD1CAT, data=finaldondat[finaldondat$TARGDOLCAT==1, ], ylim=c(0,500), xlab="CNCOD1CAT", ylab="TARGDOL")
boxplot(TARGDOL~SLCOD1CAT, data=finaldondat[finaldondat$TARGDOLCAT==1, ], ylim=c(0,500), xlab="SLCOD1CAT", ylab="TARGDOL")

######################################################################################################

# Logistic Regression

head(train)

# removed cndat1 to avoid  singularity
binfit1<-glm(TARGDOLCAT ~. - CNDAT1 - TARGDOL + factor(CNDOL1<CONLARG) 
             + factor(CONTPERTIME>10) + factor(CNMON1<30) + factor(CNDOL3>49)
             + factor(CNTRLIF>100) + factor(CNDOL1>=CNDOL2) + factor(CNTRLIF>CNDOL1+CNDOL2+CNDOL3)
               , data=train, family="binomial")
summary(binfit1)

step(binfit1, direction="both")

# removed conlarg as it has highest p value
binfit2<-
  glm(formula = TARGDOLCAT ~ CNDOL1 + CNTRLIF + CONTRFST + CNDOL2 + 
        CNDOL3 + CNTMLIF + SEX + CNMON1 + CNMONF + CNMONL + CNCOD1CAT + 
        SLCOD1CAT + CONTPERTIME + factor(CNDOL1 < CONLARG) + factor(CONTPERTIME > 10) 
      + factor(CNMON1 < 30) + factor(CNTRLIF > 100) + factor(CNDOL1 >=  CNDOL2) 
      + factor(CNTRLIF > CNDOL1 + CNDOL2 + CNDOL3), family = "binomial", 
      data = train)
summary(binfit2)

# not significantly different keep binfit2
1-pchisq(binfit2$deviance-binfit1$deviance,1)

# roc curve auc of 0.714
library(pROC)
plot.roc(train$TARGDOLCAT, binfit2$fitted.values, xlab="1-Specificity" )

######################################################################################################

# Linear Regression
head(train)

fullfit1<-lm(TARGDOL~ .- CNDAT1 - TARGDOLCAT + factor(CNDOL1 < CONLARG) + factor(CONTPERTIME > 10) 
             + factor(CNMON1 < 30) + factor(CNTRLIF > 100) + factor(CNDOL1 >= CNDOL2) 
             + factor(CNTRLIF > CNDOL1 + CNDOL2 + CNDOL3)
             ,data=subset(train, TARGDOLCAT==1, select=CNDOL1:TARGDOLCAT))
summary(fullfit1)

# stepwise regression going both directions
step(fullfit1, direction="both")

partialfit1<-
  lm(formula = TARGDOL ~ CNDOL1 + CNTRLIF + CONLARG + CONTRFST + 
       CNDOL2 + CNDOL3 + CNTMLIF + CNMONF + CNMONL + CNCOD1CAT + 
       SLCOD1CAT + CONTPERTIME + factor(CNDOL1 < CONLARG) + factor(CONTPERTIME > 10) 
     + factor(CNMON1 < 30) + factor(CNTRLIF > 100) + factor(CNDOL1 >= CNDOL2) 
     + factor(CNTRLIF > CNDOL1 + CNDOL2 + CNDOL3), data = subset(train, TARGDOLCAT == 1, select = CNDOL1:TARGDOLCAT))
summary(partialfit1)

anova(partialfit1, fullfit1)

# anova suggests using the partial model

plot(partialfit1, which=1)
plot(partialfit1, which=1, xlim=c(0,150))
plot(partialfit1, which=2)

# no variance stabilizing transformations

# delete 3 outliers with std dev more than 3
stdres<-rstandard(partialfit1)
head(stdres)

head(newdondat)
sum(abs(stdres)>3)

stdres[c("29576", "3702", "29734")]

train2<-train[-which(rownames(train) %in% c("29576", "3702", "29734")), ]

fullfit2<-
  lm(formula = TARGDOL ~ CNDOL1 + CNTRLIF + CONLARG + CONTRFST + 
       CNDOL2 + CNDOL3 + CNTMLIF + CNMONF + CNMONL + CNCOD1CAT + 
       SLCOD1CAT + CONTPERTIME + factor(CNDOL1 < CONLARG) + factor(CONTPERTIME > 10) 
     + factor(CNMON1 < 30) + factor(CNTRLIF > 100) + factor(CNDOL1 >= CNDOL2) 
     + factor(CNTRLIF > CNDOL1 + CNDOL2 + CNDOL3), data = subset(train2, TARGDOLCAT == 1, select = CNDOL1:TARGDOLCAT))
summary(fullfit2)

step(fullfit2, direction="both")

partialfit2<-
  lm(formula = TARGDOL ~ CNDOL1 + CONLARG + CONTRFST + CNDOL2 + 
       CNDOL3 + CNMONL + CNCOD1CAT + SLCOD1CAT + CONTPERTIME + factor(CONTPERTIME > 10) 
     + factor(CNDOL1 >= CNDOL2) + factor(CNTRLIF > CNDOL1 + CNDOL2 + CNDOL3), 
     data = subset(train2, TARGDOLCAT == 1, select = CNDOL1:TARGDOLCAT))
summary(partialfit2)

anova(partialfit2, fullfit2)

# use partial fit 2

plot(partialfit2, which=1)
plot(partialfit2, which=2)

# check influential obsv

plot(partialfit2, which=4)
plot(partialfit2, which=6)

install.packages("influence.ME")
library(influence.ME)

summary(partialfit2)

# remove 3 obsv with cook's distance more than 4/(n-p-1)
sum(cooks.distance(partialfit2)>4/(66131-16-1))

cooks.distance(partialfit2)[c("44239","26880","86345")]
which(rownames(train2) %in% c("44239","26880","86345"))

train3<-train2[-which(rownames(train2) %in% c("44239","26880","86345")), ]

fullfit3<-
  lm(formula = TARGDOL ~ CNDOL1 + CONLARG + CONTRFST + CNDOL2 + 
       CNDOL3 + CNMONL + CNCOD1CAT + SLCOD1CAT + CONTPERTIME + factor(CONTPERTIME > 10) 
     + factor(CNDOL1 >= CNDOL2) + factor(CNTRLIF > CNDOL1 + CNDOL2 + CNDOL3), 
     data = subset(train3, TARGDOLCAT == 1, select = CNDOL1:TARGDOLCAT))
summary(fullfit3)

step(fullfit3, direction="both")

partialfit3<-
  lm(formula = TARGDOL ~ CNDOL1 + CONLARG + CONTRFST + CNDOL2 + 
       CNDOL3 + CNMONL + CNCOD1CAT + SLCOD1CAT + CONTPERTIME + factor(CONTPERTIME > 10)
     + factor(CNDOL1 >= CNDOL2) + factor(CNTRLIF > CNDOL1 +  CNDOL2 + CNDOL3)
     , data = subset(train3, TARGDOLCAT == 1, select = CNDOL1:TARGDOLCAT))
summary(partialfit3)

anova(partialfit3, fullfit3)

# full and partial model same at end

plot(partialfit3, which=1)
plot(partialfit3, which=2)
plot(partialfit3, which=4)
plot(partialfit3, which=6)

# vif check

# partialfit3<-
#   lm(formula = TARGDOL ~ CNDOL1 + CONLARG + CONTRFST + CNDOL2 + 
#        CNDOL3 + CNMONL + CNCOD1CAT + SLCOD1CAT + CONTPERTIME + factor(CONTPERTIME > 10)
#      + factor(CNDOL1 >= CNDOL2) + factor(CNTRLIF > CNDOL1 +  CNDOL2 + CNDOL3)
#      , data = subset(train3, TARGDOLCAT == 1, select = CNDOL1:TARGDOLCAT))
# summary(partialfit3)

head(train3)
head(vifdf)
levels(train3$SLCOD1CAT)

vifdf<-train3[train3$TARGDOLCAT==1 ,c(1,3, 4, 6, 7, 13, 16)]
vifdf$CONTPERTIMEGTN10<-as.numeric(vifdf$CONTPERTIME>10)
vifdf$CNDOL1GTNECNDOL2<-as.numeric(vifdf$CNDOL1 >= vifdf$CNDOL2)
vifdf$CNTRLIFMTN3<-as.numeric(train3$CNTRLIF[train3$TARGDOLCAT==1]> 
                                     train3$CNDOL1[train3$TARGDOLCAT==1]
                                   + train3$CNDOL2[train3$TARGDOLCAT==1]
                                   + train3$CNDOL3[train3$TARGDOLCAT==1])
vifdf$CNCOD1CATB<-ifelse(train3$CNCOD1CAT[train3$TARGDOLCAT==1]=="B",1,0)
vifdf$CNCOD1CATC<-ifelse(train3$CNCOD1CAT[train3$TARGDOLCAT==1]=="C",1,0)
vifdf$CNCOD1CATD<-ifelse(train3$CNCOD1CAT[train3$TARGDOLCAT==1]=="D",1,0)
vifdf$CNCOD1CATM<-ifelse(train3$CNCOD1CAT[train3$TARGDOLCAT==1]=="M",1,0)
vifdf$SLCOD1CATB<-ifelse(train3$SLCOD1CAT[train3$TARGDOLCAT==1]=="B",1,0)
vifdf$SLCOD1CATC<-ifelse(train3$SLCOD1CAT[train3$TARGDOLCAT==1]=="C",1,0)
vifdf$SLCOD1CATD<-ifelse(train3$SLCOD1CAT[train3$TARGDOLCAT==1]=="D",1,0)
vifdf$SLCOD1CATM<-ifelse(train3$SLCOD1CAT[train3$TARGDOLCAT==1]=="M",1,0)

vifdfcor<-solve(cor(vifdf))
diag(vifdfcor)
write.csv(vifdfcor, "vifcorrtab.csv")

# only 1 VIF greater than 10

######################################################################################################

# FINAL PERFORMANCE


testnorm<-test
testnorm$PREDTARGDOL<-predict(partialfit3, testnorm)
testnorm$PROB<-predict(binfit2, newdata=testnorm, type="response")
testnorm$EY<-testnorm$PREDTARGDOL*testnorm$PROB
testnorm$ACTUALPREDDIFF<-testnorm$TARGDOL-testnorm$EY
sqrt(sum(testnorm$ACTUALPREDDIFF^2))

topnorm1000<-head(testnorm[order(-testnorm$EY),],n=1000)

sum(topnorm1000$TARGDOL)

head(topnorm1000)

actualtop1000<-head(test[order(-test$TARGDOL),],n=1000)
View(actualtop1000)

