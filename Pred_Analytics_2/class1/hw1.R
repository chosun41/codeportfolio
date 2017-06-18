setwd("C:/Users/Michael/Desktop/pred_anal2/class1")

# 2.a
#install.packages("xlsx")
options(java.home="C:\\Program Files\\Java\\jre7\\")
library(rJava)
library(xlsx)
data<-read.xlsx("HW1_data.xls", sheetName = "Prob 2")
fit<-lm(I(1/Y)~I(1/X), data=data)
summary(fit)
gamma0<-1/coef(fit)[1]
gamma0
gamma1<-coef(fit)[2]/coef(fit)[1]
gamma1

# 2.b
# nlm yhat here is 1/predicted y
fn <- function(p) {yhat<-p[1]*data$X/(p[2]+data$X); sum((data$Y-yhat)^2)} 
out<-nlm(fn,p=c(gamma0,gamma1),hessian=TRUE)
out$estimate
# fn <- function(p) {yhat<-1/p[1]+p[2]/p[1]*(newx); sum((newy-yhat)^2)} 
# out<-nlm(fn,p=c(gamma0,gamma1),hessian=TRUE)
# out$estimate
#nls
fn2 <- function(x1,p) p[1]*x1/(p[2]+x1)
out2<-nls(Y~fn2(X, p),data=data,start=list(p=c(gamma0,gamma1)),trace=TRUE)
coef(summary(out2))[,1]


# 3.a
theta<-out$estimate  #parameter estimates
MSE<-out$minimum/(length(data$Y) - length(theta))  #estimate of the error variance
InfoMat<-out$hessian/2/MSE  
CovTheta<-solve(InfoMat) 
SE<-sqrt(diag(CovTheta))
#standard errors of parameter estimates
InfoMat
CovTheta
SE
MSE

# 3.b
vcov(out2)
SEnls<-sqrt(diag(vcov(out2)))
SEnls

# 3.c
# nlm 95% conf interval
c(theta[1]-qnorm(0.975)*SE[1], theta[1]+qnorm(0.975)*SE[1])
c(theta[2]-qnorm(0.975)*SE[2], theta[2]+qnorm(0.975)*SE[2])
# nls 95% conf interval
confint.default(out2)


# 4.a
#install.packages("boot")
library(boot)
datafit<-function(Z, i, theta0){
  Zboot<-Z[i,]
  # in order it appears in the dataframe data
  y<-Zboot[[1]]; x1<-Zboot[[2]]
  fn3 <- function(p) {yhat<-p[1]*x1/(p[2]+x1); sum((y-yhat)^2)} 
  out<-nlm(fn3,p=theta0)
  theta<-out$estimate
}

databoot<-boot(data, datafit, R=20000, theta0=c(gamma0, gamma1))
# not using hessian here, since it is not outputted
CovTheta<-cov(databoot$t)
SE<-sqrt(diag(CovTheta))
SE # bias=boostrap mean theta - data theta

# histograms for model parameters
plot(databoot,index=1)
plot(databoot,index=2)

#4.b
boot.ci(databoot,conf=.95,index=1,type="norm") 
boot.ci(databoot,conf=.95,index=2,type="norm") 

#4.c
boot.ci(databoot,conf=.95,index=1,type="basic")
boot.ci(databoot,conf=.95,index=2,type="basic")

#5

datafit2<-function(Z, i, theta0, x_pred){
  Zboot<-Z[i,]
  # in order it appears in the file for Zboot
  y<-Zboot[[1]]; x1<-Zboot[[2]]
  fn3 <- function(p) {yhat<-p[1]*x1/(p[2]+x1); sum((y-yhat)^2)} 
  out<-nlm(fn3,p=theta0)
  theta<-out$estimate
  y_pred<- theta[1]*x_pred/(theta[2]+x_pred)} #predicted response

databoot2<-boot(data, datafit2, R=20000, theta0=c(gamma0, gamma1), x_pred=27)

# from 2b varYhat is variance of y pred of xpred=27
MSE<-out$minimum/(length(data$Y) - length(theta)) 
VarYhat<-var(databoot2$t); VarYhat
SEg<-sqrt(VarYhat); SEg
SEy<-sqrt(VarYhat + MSE); SEy

# conf int
c(databoot2$t0-qnorm(.975)*SEg, databoot2$t0+qnorm(.975)*SEg)

# pred int
c(databoot2$t0-qnorm(.975)*SEy, databoot2$t0+qnorm(.975)*SEy)

#6

# AIC for problem 2b
# n<-nrow(data)
# p<-2
AIC2b<- -2*as.numeric(logLik(out2))/18+2*4/18
AIC2b

# get initial guesses from linear model of new model
fit6<-lm(I(Y)~I(sqrt(X)), data=data)
summary(fit6)
coef(fit6)

# guesses from fit6 coefficients
fn4 <- function(x1,p) p[1]+p[2]*sqrt(x1)
out4<-nls(Y~fn4(X,p),data=data, start=list(p=c(coef(fit6)[1],coef(fit6)[2])),trace=TRUE)
AIC6<- -2*as.numeric(logLik(out4))/18+2*4/18
AIC6

#7
CVInd <- function(n,K) {  #n is sample size; K is number of parts; returns K-length list of indices for each part
  m<-floor(n/K)  #approximate size of each part floor(18/18)=1
  r<-n-m*K  # 18-1*18=0
  I<-sample(n,n)  #random reordering of the indices 
  Ind<-list()  #will be list of indices for all K parts
  length(Ind)<-K # 18 lists
  for (k in 1:K) {
    if (k <= r) kpart <- ((m+1)*(k-1)+1):((m+1)*k)  # not applicable here since r is 0
    else kpart<-((m+1)*r+m*(k-r-1)+1):((m+1)*r+m*(k-r)) 
    Ind[[k]] <- I[kpart]  #indices for kth part of data
  }
  Ind
}

Nrep<-20 #number of replicates of CV
n.models = 2 #number of different models to fit and compare
# 1st model is model from 2b and 2nd model is sq rt model
fn2 <- function(x1,p) p[1]*x1/(p[2]+x1)
fn4 <- function(x1,p) p[1]+p[2]*sqrt(x1)
n<-nrow(data)
K<-n #K-fold CV on each replicate, using n fold here
yhat=matrix(0,n,n.models)
MSE<-matrix(0,Nrep,n.models)

for (j in 1:Nrep) {
  Ind<-CVInd(n,K)
  for (k in 1:K) {
    out<-nls(Y~fn2(X,p),data=data[-Ind[[k]],],start=list(p=c(gamma0,gamma1)))
    yhat[Ind[[k]],1]<-as.numeric(predict(out,data[Ind[[k]],]))
    out<-nls(Y~fn4(X,p),data=data[-Ind[[k]],],start=list(p=c(coef(fit6)[1],coef(fit6)[2])))
    yhat[Ind[[k]],2]<-as.numeric(predict(out,data[Ind[[k]],]))
  } #end of k loop
  print(j)
  print(yhat)
  print(MSE)
  MSE[j,]=apply(yhat,2,function(indvyhat) sum((data$Y-indvyhat)^2))/n
} #end of j loop

MSEAve<- apply(MSE,2,mean); MSEAve #averaged mean square CV error
MSEsd <- apply(MSE,2,sd); MSEsd   #SD of mean square CV error
r2<-1-MSEAve/var(data$Y); r2  #CV r^2

#8
plot(data$X, data$Y-predict(out2,data$X))
plot(data$X, data$Y-predict(out4,data$X))


