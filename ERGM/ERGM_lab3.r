install.packages("statnet")
install.packages("coda")
library(statnet)

####################
#prepare the working directory
#go to Session > Set Working Directory > To Source File Location
####################
list.files() #this will list the files in the current working directory to see if you're in the right directory

####################
####################

# load the network file
A <-matrix(scan("CRIeq.txt", n=17*17), 17, 17, byrow = TRUE) #this makes an R matrix from the CRIeq file
CRIeq <- as.network.matrix(A, matrix.type="adjacency") #this turns that matrix into a network

#load the attribute file
att <- read.table("EXeq_cons.txt", header = T) #this reads the attribute table and saves in in a variable called 'att'
att <- as.vector(att) #this converts those attributes into an R vector
set.vertex.attribute(CRIeq, "EX", att, v=1:17) #this sets those attributes as vertex attributes in the network you created above

## load the covariate network  
B <- matrix(scan("CAIeq.txt", n =17*17), 17, 17, byrow =TRUE) #reads in the CAIeq file and saves it as an R matrix
CAIeq <- as.network.matrix(B, matrix.type="adjacency") #converts that matrix into a network

####################
## to save current objects in R, which can be reloaded later using load command
## save.image("Lab3_files.RData")
## load("Lab3_files.RData")
## ls() 
####################


####################
# To get some basic information
####################
summary(CRIeq) #summarize the CRIeq network
network.size(CRIeq) #print out the network size
plot(CRIeq) #plot the network
plot(CRIeq,displaylabels=T,boxed.labels=F) #plot it with the labels
betweenness(CRIeq) #calculate betweenness for the network
isolates(CRIeq) #find the isolates in the network

summary(CAIeq) #summarize the CAIeq network
plot(CAIeq,displaylabels=T,boxed.labels=F) #plot the CAIeq network with labels
####################
# build the model
# to find all parameters:
# help('ergm-terms')
####################
model <- ergm(CRIeq ~ edges + mutual + gwesp(0.2, fixed=T) + edgecov(CAIeq) +nodeicov("EX")+ nodeocov("EX")) #runs the ergm model
summary(model) #summarizes the ergm model

####################
#model diagnostic
####################
pdf("modeldiagnostics.pdf") #saves the model diagnostic as a PDF - look for this in your current working directory
mcmc.diagnostics(model)	#performs the markov chain monte carlo diagnostics
dev.off()

####################
#goodness of fit
####################
# to check how well the estimated model captures certain features of the observed network, 
# for example triangles in the network.
###
sim <- simulate(model, burnin=100000, interval =100000, nsim=100, verbose=T) #Uses the ergm model to simulate a null model
# look at the simulated networks
plot(sim[[1]]) #plot the first of the simulated networks
plot(sim[[10]]) #plot the 10th simulated network

##
# extract the number of triangles from each of the 100 samples and
# compare the distribution of triangles in the sampled networks with the observed network
##
model.tridist <- sapply(1:100, function(x) summary(sim[[x]] ~triangle)) #3extracts the tiangle data from the simulated networks
hist(model.tridist, xlim=c(20,120)) #plots that triangle distribution as a histogram
CRIeq.tri <-summary(CRIeq ~triangle) #saves the CRIeq triangle data from the summary to the CRI.eq variable
arrows(CRIeq.tri,20, CRIeq.tri, 5, col="red", lwd=3) #adds an arrow to the plotted histogram

####################
## compiles statistics for these simulations as well as the observed network, and calculates p-values 
####################
gof <- gof(model~idegree+odegree+espartners+distance, verbose=T, burnin=1e+5, interval=1e+5) #tests the goodness of fit of the model

par(mfrow=c(2,2)) #separates the plot window into a 2 by 2 orientation
plot(gof) #plots the goodness of fit

gof #displays the goodness of fit info in the console
