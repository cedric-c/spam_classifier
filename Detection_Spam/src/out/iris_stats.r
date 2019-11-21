library(ggplot2)
library(dplyr)
file<-"iris_aggregate.csv"
iris<-read.csv(file)

sp <- ggplot(iris, aes(x=k, y=correct_over_total, colour=trainingSize, size=testingSize))