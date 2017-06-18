# Question 3
setwd("C:/Users/mcho/Desktop/data_mining")
pergame36<-read.csv("pergame36.csv")
pergame36 <- pergame36[complete.cases(pergame36),]
# take out index and two point variables
pergame36<-pergame36[,-c(1,20,21)]
#476/483 players

# no position based scales
library(psych)
alpha(pergame36, check.keys=T, warning=F)
head(pergame36)
# take out minutes played as this raises alpha
pergame36<-pergame36[,-c(20)]
# take out plus minus as this raises alpha
pergame36<-pergame36[,-c(18)]
# take out total points, rebounds as this raises alpha and causes singularity
pergame36<-pergame36[,-c(6,9)]
# take out att variables as this raises alpha and/or has high correlation with made variables
pergame36<-pergame36[,-c(3,5,15)]
# take out advanced pcnt variables except tov and usgpcnt % as they have high correlation
# with base stats of same name
pergame36<-pergame36[,-c(19,21:26)]
# remove fgpcnt as this has high correlation with efgpcnt
pergame36<-pergame36[,-c(18)]
# remove field goals made as this raises alpha
pergame36<-pergame36[,-c(2)]
# remove free throws made as this raises alpha
pergame36<-pergame36[,-c(2)]

# check correlations since principal requires a non singular matrix, none above 0.9 after 
# removing variables
head(pergame36)
round(cor(pergame36[ ,-c(1,11)]),1)

# check distribution for skewness
library(reshape2)
library(ggplot2)
ggplot(data = melt(pergame36), mapping = aes(x = value)) + 
  geom_histogram(bins = 10) + facet_wrap(~variable, scales = 'free_x')

# seems we should take a square root of the following heavily skewed variables
pergame36$ASSISTS<-sqrt(pergame36$ASSISTS)
pergame36$AST2TRNVR<-sqrt(pergame36$AST2TRNVR)
pergame36$FTAPERFGA<-sqrt(pergame36$FTAPERFGA)
pergame36$BLOCKED_SHOTS<-sqrt(pergame36$BLOCKED_SHOTS)
pergame36$STEALS<-sqrt(pergame36$STEALS)
pergame36$OFFENSIVE_REBOUNDS<-sqrt(pergame36$OFFENSIVE_REBOUNDS)
pergame36$THREE_POINT_MADE<-sqrt(pergame36$THREE_POINT_MADE)
pergame36$DISQUALIFICATIONS<-sqrt(pergame36$DISQUALIFICATIONS)

# check skew and alpha again
ggplot(data = melt(pergame36), mapping = aes(x = value)) + 
  geom_histogram(bins = 10) + facet_wrap(~variable, scales = 'free_x')
alpha(pergame36, check.keys=T, warning=F)
# remove disqualifications as the skewness does not change
head(pergame36)
pergame36<-pergame36[,-c(6)]
ggplot(data = melt(pergame36), mapping = aes(x = value)) + 
  geom_histogram(bins = 10) + facet_wrap(~variable, scales = 'free_x')

# scale creation

library(psych)

# pick 4 factors scree plot
par(mfrow=c(1,1))
fit<-principal(pergame36[ ,-c(1,10)], rotate="none")
fit$values
plot(fit$values, type="b")
abline(h=1)

# make scales in iterations
fit2<-principal(pergame36[ ,-c(1,10)], nfactor=4)
fit2$loadings
alpha(pergame36[ ,-c(1,10)], check.keys=T, warning=F)
dim(fit2$loadings)
mat<-matrix(fit2$loadings, nrow=15, ncol=4)
df<-data.frame(mat)
rownames(df)<-colnames(pergame36[ ,-c(1,10)])
colnames(df)<-c("RC1","RC3", "RC2","RC4") 
# only keep high loadings and get rid of those high cross loadings
var1<-rownames(df[df$RC1>0.30,])
var2<-rownames(df[df$RC2>0.30,])
var3<-rownames(df[df$RC3>0.30,])
var4<-rownames(df[df$RC4>0.30,])
clmns<-union(union(union(var1,var2),var3),var4)
# sort alphabetically
clmns<-sort(clmns)
clmns

fit2<-principal(pergame36[ ,clmns], nfactor=4)
fit2$loadings
alpha(pergame36[ ,clmns], check.keys=T, warning=F)
# remove defensive rebounds as this raises alpha
clmns<-clmns[-4]
clmns

fit2<-principal(pergame36[ ,clmns], nfactor=4)
fit2$loadings
alpha(pergame36[ ,clmns], check.keys=T, warning=F)
# remove turnovers as this raises alpha
clmns<-clmns[-12]
clmns

fit2<-principal(pergame36[ ,clmns], nfactor=4)
fit2$loadings
alpha(pergame36[ ,clmns], check.keys=T, warning=F)
dim(fit2$loadings)
mat<-matrix(fit2$loadings, nrow=12, ncol=4)
df<-data.frame(mat)
rownames(df)<-colnames(pergame36[ ,clmns])
colnames(df)<-c("RC1","RC2", "RC3","RC4")
# only keep high loadings and get rid of those high cross loadings
var1<-rownames(df[df$RC1>0.30,])
var2<-rownames(df[df$RC2>0.30,])
var3<-rownames(df[df$RC3>0.30,])
var4<-rownames(df[df$RC4>0.30,])
clmns<-union(union(union(var1,var2),var3),var4)
# sort alphabetically
clmns<-sort(clmns)
clmns
# remove usgpcnt as this doesn't fit
clmns<-clmns[-12]

fit2<-principal(pergame36[ ,clmns], nfactor=4)
fit2$loadings
alpha(pergame36[ ,clmns], check.keys=T, warning=F)
# remove tovpcnt as this raises alpha
clmns<-clmns[-11]
clmns

fit2<-principal(pergame36[ ,clmns], nfactor=4)
fit2$loadings
alpha(pergame36[ ,clmns], check.keys=T, warning=F)
# remove steal as this doesn't fit
clmns<-clmns[-8]
clmns

fit2<-principal(pergame36[ ,clmns], nfactor=4)
fit2$loadings
alpha(pergame36[ ,clmns], check.keys=T, warning=F)
dim(fit2$loadings)
mat<-matrix(fit2$loadings, nrow=9, ncol=4)
df<-data.frame(mat)
rownames(df)<-colnames(pergame36[ ,clmns])
colnames(df)<-c("RC1","RC3", "RC2","RC4")
# only keep high loadings and get rid of those high cross loadings
var1<-rownames(df[df$RC1>0.40,])
var2<-rownames(df[df$RC2>0.40,])
var3<-rownames(df[df$RC3>0.40,])
var4<-rownames(df[df$RC4>0.40,])
clmns<-union(union(union(var1,var2),var3),var4)
# sort alphabetically
clmns<-sort(clmns)
clmns

#final scales
scales<-data.frame(fit2$scores)
head(fit2$scores)
var1
var2
var3
var4
colnames(scales)<-c("PAINT_SKILLS","PRMT_SHOOTING", "BALL_HANDLING","FT_LINE")
alpha(pergame36[ ,var1], check.keys=T, warning=F)
alpha(pergame36[ ,var2], check.keys=T, warning=F)
alpha(pergame36[ ,var3], check.keys=T, warning=F)
alpha(pergame36[ ,var4], check.keys=T, warning=F)
head(scales)
# reattach player and positions
scales$PLAYER=pergame36$PLAYER
scales$POSITION=pergame36$POSITION

write.csv(scales, file = "scales.csv")

ggplot(data = melt(scales), mapping = aes(x = value)) + 
  geom_histogram(bins = 10) + facet_wrap(~variable, scales = 'free_x')

# clustering functions
summary.kmeans = function(fit)
{
  p = ncol(fit$centers)
  k = nrow(fit$centers)
  n = sum(fit$size)
  sse = sum(fit$withinss)
  xbar = t(fit$centers)%*%fit$size/n
  ssb = sum(fit$size*(fit$centers - rep(1,k) %*% t(xbar))^2)
  print(data.frame(
    n=c(fit$size, n),
    Pct=(round(c(fit$size, n)/n,2)),
    round(rbind(fit$centers, t(xbar)), 2),
    RMSE = round(sqrt(c(fit$withinss/(p*fit$size-1), sse/(p*(n-k)))), 4)
  ))
  cat("SSE = ", sse, "; SSB = ", ssb, "\n")
  cat("R-Squared = ", ssb/(ssb+sse), "\n")
  cat("Pseudo F = ", (ssb/(k-1))/(sse/(n-k)), "\n\n");
  invisible(list(sse=sse, ssb=ssb, Rsqr=ssb/(ssb+sse), F=(ssb/(k-1))/(sse/(n-k))))
}

plot.kmeans = function(fit,boxplot=F)
{
  require(lattice)
  p = ncol(fit$centers)
  k = nrow(fit$centers)
  plotdat = data.frame(
    mu=as.vector(fit$centers),
    clus=factor(rep(1:k, p)),
    var=factor( 0:(p*k-1) %/% k, labels=colnames(fit$centers))
  )
  print(dotplot(var~mu|clus, data=plotdat,
                panel=function(...){
                  panel.dotplot(...)
                  panel.abline(v=0, lwd=.1)
                },
                layout=c(k,1),
                xlab="Cluster Mean"
  ))
  invisible(plotdat)
}
library(mclust)

# develop clusters using GMMs

# k means indicates 4 clusters

fit<-Mclust(scales[,c(1:4)])
plot(fit, what="classification")

# size, averages, and model parameters 6 VEE
round(fit$parameter$pro * nrow(scales),0)
fit$parameter$variance$G
fit$parameter$variance$modelName
t(fit$parameter$mean)

# range of scales
summary(scales)

# cluster assignments
colnames(fit$z)<-c("1","2","3","4","5","6")
scales$cluster<-colnames(fit$z)[apply(fit$z,1,which.max)]
# combo guards - avg paint, avg prmt shooting, high ball handling, high ftpcnt
scales$PLAYER[scales$cluster=="1"]
table(scales$cluster=="1", scales$POSITION)[2,]
# skilled bigs - medium prmt shooting, medium paint skills, avg ball hSSandling, low ftpcnt
scales$PLAYER[scales$cluster=="2"]
table(scales$cluster=="2", scales$POSITION)[2,]
# role player - low paint, avg prmt shooting, avg ball handling, high ftpcnt
scales$PLAYER[scales$cluster=="3"]
table(scales$cluster=="3", scales$POSITION)[2,]
# shooters - avg paint, high prmt shooting, avg ball handling, high ftpcnt
scales$PLAYER[scales$cluster=="4"]
table(scales$cluster=="4", scales$POSITION)[2,]
# classic bigs - high paint, low prmt shooting, low ball handling, avg ftpcnt
scales$PLAYER[scales$cluster=="5"]
table(scales$cluster=="5", scales$POSITION)[2,]
# scrubs- low paint, low prmt shooting, low ball handling, low ftpcnt
scales$PLAYER[scales$cluster=="6"]
table(scales$cluster=="6", scales$POSITION)[2,]
