setwd("C:/Users/Michael/Desktop/pred_anal2/class2")
# install.packages("xlsx")
# install.packages('rJava')
options(java.home="C:\\Program Files\\Java\\jre7\\")
library(rJava)
library(xlsx)
heart<-read.xlsx("HW2_data.xls", sheetName= 1)

# first column has row index get rid of it
heart<-heart[,2:9]

# cost to log10 cost response
heart$cost<-log10(heart$cost)

# standardized predictor dataset and unstandardized predictor dataset
standheart<-heart
standheart[ , c(2,4:8)]<-scale(standheart[ ,c(2,4:8)], center=T, scale=T)
unstandheart<-heart

# problem 1
regfit<-lm(cost~., unstandheart)
summary(regfit)
n<-nrow(unstandheart)
# training MSE 0.30
sum((unstandheart$cost-predict(regfit, unstandheart))^2)/n

pairs(unstandheart)
round(cor(unstandheart),2)
# the number of interventions/procedures carried out and number of days of treatment condition
# seemed to have the biggest effect on cost

library(reshape2)
library(ggplot2)
ggplot(data = melt(unstandheart), mapping = aes(x = value)) + 
        geom_histogram(bins = 10) + facet_wrap(~variable, scales = 'free_x')
# drugs, number of comorbidites, and number of days of treatment are heavily right skewed
plot(regfit, which=1)
plot(regfit, which=2)

# problem 2

# install.packages("nnet")
library(nnet)

# linout=T for continous values
CVInd <- function(n,K) {  #n is sample size; K is number of parts; returns K-length list of indices for each part
        m<-floor(n/K)  #approximate size of each part
        r<-n-m*K
        I<-sample(n,n)  #random reordering of the indices
        Ind<-list()  #will be list of indices for all K parts
        length(Ind)<-K
        for (k in 1:K) {
                if (k <= r) kpart <- ((m+1)*(k-1)+1):((m+1)*k)
                else kpart<-((m+1)*r+m*(k-r-1)+1):((m+1)*r+m*(k-r))
                Ind[[k]] <- I[kpart]  #indices for kth part of data
        }
        Ind
}

Nrep<-3 #number of replicates of CV
K<-10  #K-fold CV on each replicate
n.models = 9 #number of different models to fit
n=nrow(standheart)
y<-standheart$cost
yhat=matrix(0,n,n.models)
MSE<-matrix(0,Nrep,n.models)

for (j in 1:Nrep) {
        Ind<-CVInd(n,K)
        for (k in 1:K) {
                out<-nnet(cost~.,standheart[-Ind[[k]],], linout=T, skip=F, size=25, 
                          decay=0.5, maxit=100, trace=F)
                yhat[Ind[[k]],1]<-as.numeric(predict(out,standheart[Ind[[k]],]))
                out<-nnet(cost~.,standheart[-Ind[[k]],], linout=T, skip=F, size=25, 
                          decay=1, maxit=100, trace=F)
                yhat[Ind[[k]],2]<-as.numeric(predict(out,standheart[Ind[[k]],]))
                out<-nnet(cost~.,standheart[-Ind[[k]],], linout=T, skip=F, size=25, 
                          decay=2, maxit=100, trace=F)
                yhat[Ind[[k]],3]<-as.numeric(predict(out,standheart[Ind[[k]],]))
                out<-nnet(cost~.,standheart[-Ind[[k]],], linout=T, skip=F, size=30, 
                          decay=0.5, maxit=100, trace=F)
                yhat[Ind[[k]],4]<-as.numeric(predict(out,standheart[Ind[[k]],]))
                out<-nnet(cost~.,standheart[-Ind[[k]],], linout=T, skip=F, size=30, 
                          decay=1, maxit=100, trace=F)
                yhat[Ind[[k]],5]<-as.numeric(predict(out,standheart[Ind[[k]],]))
                out<-nnet(cost~.,standheart[-Ind[[k]],], linout=T, skip=F, size=30, 
                          decay=2, maxit=100, trace=F)
                yhat[Ind[[k]],6]<-as.numeric(predict(out,standheart[Ind[[k]],]))
                out<-nnet(cost~.,standheart[-Ind[[k]],], linout=T, skip=F, size=35, 
                          decay=0.5, maxit=100, trace=F)
                yhat[Ind[[k]],7]<-as.numeric(predict(out,standheart[Ind[[k]],]))
                out<-nnet(cost~.,standheart[-Ind[[k]],], linout=T, skip=F, size=35, 
                          decay=1, maxit=100, trace=F)
                yhat[Ind[[k]],8]<-as.numeric(predict(out,standheart[Ind[[k]],]))
                out<-nnet(cost~.,standheart[-Ind[[k]],], linout=T, skip=F, size=35, 
                          decay=2, maxit=100, trace=F)
                yhat[Ind[[k]],9]<-as.numeric(predict(out,standheart[Ind[[k]],]))
        } #end of k loop
        MSE[j,]=apply(yhat,2,function(x) sum((y-x)^2))/n
} #end of j loop
MSE
MSEAve<- apply(MSE,2,mean); MSEAve #averaged mean square CV error
MSEsd <- apply(MSE,2,sd); MSEsd   #SD of mean square CV error
r2<-1-MSEAve/var(y); r2  #CV r^2
# lowest MSE around 0.22 with 68% R^2

# best model parameters are size=25 and decay=1
nn.fit<-nnet(cost~.,standheart, linout=T, skip=F, size=25, decay=1, maxit=10, trace=F)
# training MSE of 0.228
sum((standheart$cost-predict(nn.fit, standheart))^2)/n

# install.packages("yaImpute")
# install.packages("ALEPlot")
# path <- './ALEPlot_1.0.tar.gz'
# install.packages(path, repos = NULL, type="source")
library(ALEPlot)
yhat <- function(X.model, newdata) as.numeric(predict(X.model, newdata))
par(mfrow=c(2,4))
for (j in 1:7)  {ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=j, K=50, NA.plot = TRUE)
        rug(heart[,j]) }  ## This creates main effect ALE plots for all 7 predictors

# main effects 
# age= -0.08, gender= - -0.01, intvn= 2, drugs= - 0.5, ervis = 0.25, comp - 0.25, comorb = 1.5
age<-matrix(rep(c(0.06,-0.02),2),nrow=2)
age[2,1]-age[1,1]

gender<-matrix(rep(c(0.005, -0.005),2),nrow=2)
gender[2,1]-gender[1,1]

intvn<-matrix(rep(c(-0.5, 1.5),2),nrow=2)
intvn[2,1]-intvn[1,1]

drugs<-matrix(rep(c(0, -0.5),2),nrow=2)
drugs[2,1]-drugs[1,1]

ervis<-matrix(rep(c(-0.05, 0.2),2),nrow=2)
ervis[2,1]-ervis[1,1]

comp<-matrix(rep(c(-0.15, 0.1),2),nrow=2)
comp[2,1]-comp[1,1]

comorb<-matrix(rep(c(0,1.5),2),nrow=2)
comorb[2,1]-comorb[1,1]

# second order interactions
par(mfrow=c(3,2))  ## This creates 2nd-order interaction ALE plots
a<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(1,3), K=50, NA.plot = TRUE)
b<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(1,4), K=50, NA.plot = TRUE)
c<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(1,5), K=50, NA.plot = TRUE)
d<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(1,6), K=50, NA.plot = TRUE)
e<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(1,7), K=50, NA.plot = TRUE)

par(mfrow=c(2,2))
a<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(3,4), K=50, NA.plot = TRUE)
b<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(3,5), K=50, NA.plot = TRUE)
c<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(3,6), K=50, NA.plot = TRUE)
d<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(3,7), K=50, NA.plot = TRUE)

intvncomp<-matrix(c(0,0.4, 0.1, -0.2),byrow=T, nrow=2)
intvncomp
intvncomp[2,2]-intvncomp[1,2]
intvncomp[2,1]-intvncomp[1,1]
# invtn and high comp has negative effect on cost

intvncomorb<-matrix(c(0,0.6, 0.2, -1),byrow=T, nrow=2)
intvncomorb
intvncomorb[2,2]-intvncomorb[1,2]
intvncomorb[2,1]-intvncomorb[1,1]
# invtn and comp has negative effect on cost

par(mfrow=c(2,2))
a<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(4,5), K=50, NA.plot = TRUE)
b<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(4,6), K=50, NA.plot = TRUE)
c<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(4,7), K=50, NA.plot = TRUE)

drugscomp<-matrix(c(0,0.7,0,0),byrow=T, nrow=2)
drugscomp
drugscomp[2,2]-drugscomp[1,2]
drugscomp[2,1]-drugscomp[1,1]

par(mfrow=c(1,2))
a<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(5,6), K=50, NA.plot = TRUE)
b<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(5,7), K=50, NA.plot = TRUE)

par(mfrow=c(1,1))
a<-ALEPlot(standheart[,2:8], nn.fit, pred.fun=yhat, J=c(6,7), K=50, NA.plot = TRUE)

compcomorb<-matrix(c(0,0, 0, -0.6),byrow=T, nrow=2)
compcomorb
compcomorb[2,2]-compcomorb[1,2]
compcomorb[2,1]-compcomorb[1,1]
# comp and comorb has negative effect on cost

par(mfrow=c(1,1))
resid<-standheart$cost-predict(nn.fit, standheart)
df<-data.frame(cbind(resid,predict(nn.fit, standheart)))
colnames(df)<-c("Residual", "Fitted")
df <- df[order(df$Residual),]
plot(df$Fitted, df$Residual)
abline(h=0)
# the variance seems to be random

# Problem 3
library(rpart)
control <- rpart.control(minbucket = 5, cp = 0.0001, maxsurrogate = 0, usesurrogate = 0, xval = 10) 
# minbucket minimum number of obsv, cp is complexity parameter
orgtree <- rpart(cost ~ .,unstandheart, method = "anova", control = control)
plotcp(orgtree)  #plot of CV r^2 vs. size

Nrep<-100 #number of replicates of CV
n<-nrow(orgtree$cptable)
y<-unstandheart$cost
xerror=matrix(0,n,Nrep)
rownames(xerror)<-round(orgtree$cptable[,1],8)
# rownames are cp paramter

#running 10 replicates of 10-fold cross validation
for (j in 1:Nrep){
        orgtreecv <- rpart(cost ~ .,unstandheart, method = "anova", control = control)   
        df<-data.frame(orgtreecv$cptable)
        xerror[ ,j]<-df[ ,4] # fill xerror table of 10 replicates
}
xerrorAve<-apply(xerror,1,mean); xerrorAve #averaged mean square CV error
xerrorAve[which(xerrorAve==min(xerrorAve))]
xerrorsd<-apply(xerror,1,sd); xerrorsd
r2<-1-xerrorAve; r2[which(xerrorAve==min(xerrorAve))]  #CV r^2
# cv r2 of 65%

#prune back to optimal size, according to plot of CV 1-r^2
orgtree2 <- prune(orgtree, cp=0.00351925)  #approximately the best is with complexity parameter of 0.00409089
orgtree2$variable.importance
# training r2 of 70.597
1-orgtree2$cptable[nrow(orgtree2$cptable),3] #shows training and CV 1-r^2, and other things
# pruned tree plot and text
orgtree2
par(cex=.9); plot(orgtree2, uniform=F); text(orgtree2, use.n = T); par(cex=1)
# training MSE 0.2012
sum((unstandheart$cost-predict(orgtree2, unstandheart))^2)/nrow(unstandheart)

par(mfrow=c(1,1))
resid<-unstandheart$cost-predict(orgtree2, unstandheart)
df<-data.frame(cbind(resid,predict(orgtree2, unstandheart)))
colnames(df)<-c("Residual", "Fitted")
df <- df[order(df$Residual),]
plot(df$Fitted, df$Residual)
abline(h=0)
# errors seems centered around 0

# Problem 4
glass<-read.xlsx("HW2_data.xls", sheetName= 2)
# first column has row index get rid of it
glass<-glass[,2:11]

# dataset of standardized predictors and unstandardized predictors
unstandglass<-glass
standglass<-glass
standglass[ , c(1:9)]<-scale(standglass[ , c(1:9)], center=T, scale=T)

# using misclassification rate to decide between models, 
# use class in predict function 
Nrep<-10 #number of replicates of CV
K<-10  #K-fold CV on each replicate
n.models = 9 #number of different models to fit
n=nrow(standglass)
y<-standglass$type
yhat=matrix('',n,n.models)
MSC<-matrix(0,Nrep,n.models)

for (j in 1:Nrep) {
        Ind<-CVInd(n,K)
        for (k in 1:K) {
                out<-nnet(type~.,standglass[-Ind[[k]],], linout=F, skip=F, size=20, decay=0.05, maxit=100, trace=F)
                yhat[Ind[[k]],1]<-as.character(predict(out,standglass[Ind[[k]],], type="class"))
                out<-nnet(type~.,standglass[-Ind[[k]],], linout=F, skip=F, size=20, decay=0.1, maxit=100, trace=F)
                yhat[Ind[[k]],2]<-as.character(predict(out,standglass[Ind[[k]],], type="class"))
                out<-nnet(type~.,standglass[-Ind[[k]],], linout=F, skip=F, size=20, decay=0.15, maxit=100, trace=F)
                yhat[Ind[[k]],3]<-as.character(predict(out,standglass[Ind[[k]],], type="class"))
                out<-nnet(type~.,standglass[-Ind[[k]],], linout=F, skip=F, size=30, decay=0.05, maxit=100, trace=F)
                yhat[Ind[[k]],4]<-as.character(predict(out,standglass[Ind[[k]],], type="class"))
                out<-nnet(type~.,standglass[-Ind[[k]],], linout=F, skip=F, size=30, decay=0.1, maxit=100, trace=F)
                yhat[Ind[[k]],5]<-as.character(predict(out,standglass[Ind[[k]],], type="class"))
                out<-nnet(type~.,standglass[-Ind[[k]],], linout=F, skip=F, size=30, decay=0.15, maxit=100, trace=F)
                yhat[Ind[[k]],6]<-as.character(predict(out,standglass[Ind[[k]],], type="class"))
                out<-nnet(type~.,standglass[-Ind[[k]],], linout=F, skip=F, size=40, decay=0.05, maxit=100, trace=F)
                yhat[Ind[[k]],7]<-as.character(predict(out,standglass[Ind[[k]],], type="class"))
                out<-nnet(type~.,standglass[-Ind[[k]],], linout=F, skip=F, size=40, decay=0.1, maxit=100, trace=F)
                yhat[Ind[[k]],8]<-as.character(predict(out,standglass[Ind[[k]],], type="class"))
                out<-nnet(type~.,standglass[-Ind[[k]],], linout=F, skip=F, size=40, decay=0.15, maxit=100, trace=F)
                yhat[Ind[[k]],9]<-as.character(predict(out,standglass[Ind[[k]],], type="class"))
        } #end of k loop
        MSC[j,]=apply(yhat,2,function(x) sum(y!=x)/n)
} #end of j loop
MSCAve<- apply(MSC,2,mean); MSCAve #averaged mean misclassification rate lowest of 0.2735981
MSCsd<-apply(MSC,2,sd);MSCsd

# best model parameters are size=40 and decay=0.05
nn.fit2<-nnet(type~.,standglass, linout=F, skip=F, size=30, decay=0.05, maxit=10, trace=F)
# sum of squared errors
1-sum(glass$type==predict(nn.fit2,standglass, type="class"))/nrow(standglass)
# 33.17% misclassification rate

# classification tree

control <- rpart.control(minbucket = 5, cp = 0.0001, maxsurrogate = 0, usesurrogate = 0, xval = 10) 
# minbucket minimum number of obsv, cp is complexity parameter
classtree <- rpart(type ~ .,unstandglass, method = "class", control = control)
plotcp(classtree)  #plot of CV r^2 vs. size

Nrep<-100 #number of replicates of CV
n<-nrow(classtree$cptable)
y<-unstandglass$type
xerror=matrix(0,n,Nrep)
rownames(xerror)<-round(classtree$cptable[,1],7)
# rownames are cp paramter

#running 10 replicates of 10-fold cross validation
for (j in 1:Nrep){
        classtreecv <- rpart(type ~ .,unstandglass, method = "class", control = control)
        df<-data.frame(classtreecv$cptable)
        xerror[ ,j]<-df[ ,4] # fill xerror table of 10 replicates
}
xerrorAve<-apply(xerror,1,mean); xerrorAve #averaged mean square CV error
xerrorAve[which(xerrorAve==min(xerrorAve))]
xerrorsd<-apply(xerror,1,sd); xerrorsd
# cv misclass rate is 0.2995 with optimal cp is 0.0144928
xerrorAve*(1-max(table(y))/nrow(unstandglass))

#prune back to optimal size, according to plot of CV 1-r^2
classtree2 <- prune(classtree, cp=0.0144928)  #approximately the best size pruned tree is with complexity
# parameter of 0.0144928

# how much gini decreases by
classtree2$variable.importance
# training misclass % is 0.228972
classtree2$cptable[nrow(classtree2$cptable),3]*(1-max(table(y))/nrow(unstandglass)) 
#shows training and CV 1-r^2, and other things
classtree2
par(cex=.9); plot(classtree2, uniform=F); text(classtree2, use.n = T); par(cex=1)

#multinomial logistic regression
library(MASS)
Nrep<-20 #number of replicates of CV
K<-10  #K-fold CV on each replicate
n.models = 1 #number of different models to fit
n=nrow(unstandglass)
y<-unstandglass$type
yhat=matrix('',n,n.models)
MSC<-matrix(0,Nrep,n.models)

for (j in 1:Nrep) {
        Ind<-CVInd(n,K)
        for (k in 1:K) {
                out<-multinom(type~., unstandglass[-Ind[[k]],], trace=F)
                yhat[Ind[[k]],1]<-as.character(predict(out,unstandglass[Ind[[k]],], type="class"))
        } #end of k loop
        MSC[j,]=apply(yhat,2,function(x) sum(y!=x)/n)
} #end of j loop
MSCAve<- apply(MSC,2,mean); MSCAve 
MSCsd<- apply(MSC,2,sd); MSCsd 
# cv 0.38 misclassified

multi<-multinom(type~., unstandglass, trace=F)
summary(multi)
# training 26.64% misclassified
1-sum(glass$type==predict(multi, unstandglass,type="class"))/nrow(unstandglass)

# comparing other categories to Containers, one unit increase increases log odss of head and table vs cont
# except for k, ca, a
# other categories one unit increases decreases log odds compared to container

# use neural network lowest misclassification rate
