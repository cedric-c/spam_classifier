
# 
# cclem054
# 

library(ggplot2)
spam_ham<-read.csv("spam_or_ham.csv")
spam_ham_n<-read.csv("spam_or_ham_normalized.csv")
spam_ham_nb<-read.csv("spam_or_ham_normalized_balanced_actual.csv")


display_plot <- function(data) {
    plot<-ggplot(data, aes(x=K, y=ratio, color=trainingSize))
    plot + geom_point(alpha=.5, position="jitter", size=1.3) + scale_size_area() + geom_abline()
}

display_plot <- function(data, Title, X, Y, X_label, Y_label) {
    plot<-ggplot(data, aes(x=X, y=Y))
    plot + geom_point(alpha=.5, position="jitter", size=1.3) + 
        scale_size_area() +
        labs(x = X_label, y = Y_label, title=Title)
}

display_plot <- function(data, Title, X, Y, X_label, Y_label, Colour, Colour_label) {
    plot<-ggplot(data, aes(x=X, y=Y, color=Colour))
    plot + geom_point(alpha=.5, position="jitter", size=1.3) + 
        scale_size_area() +
        labs(x = X_label, y = Y_label, title=Title, color=Colour_label)
}



display_plot <- function(data, Title, X, Y, X_label, Y_label, Colour_label) {
    d <- data
    d$KGroups <- cut(d$K, breaks=c(0, 3, 6, 9, 12),
        labels=c("(0, 3)", "(3, 6)", "(6, 9)", "(9, 12)"))
    plot<-ggplot(d, aes(x=X, y=Y, color=KGroups))
    plot + geom_point(alpha=.5, position="jitter", size=1.3) + 
        scale_size_area() +
        labs(x = X_label, y = Y_label, title=Title, color=Colour_label) +
        geom_smooth(method="lm", se=FALSE)
}


display_plot_18 <- function(data, Title, X, Y, X_label, Y_label, Colour_label) {
    d <- data
    d$KGroups <- cut(d$K, breaks=c(0, 3, 6, 9, 12, 15, 18, 21),
        labels=c("(0, 3)", "(3, 6)", "(6, 9)", "(9, 12)", "(12, 15)", "(15, 18)", "(18, 21)"))
    plot<-ggplot(d, aes(x=X, y=Y, color=KGroups))
    plot + geom_point(alpha=.5, position="jitter", size=1.3) + 
        scale_size_area() +
        labs(x = X_label, y = Y_label, title=Title, color=Colour_label) +
        geom_smooth(method="lm", se=FALSE)
}

# Categorize into <100 and >=100 groups
# hw$weightGroup <- cut(hw$weightLb, breaks=c(-Inf, 100, Inf),
                      # labels=c("< 100", ">= 100"))


# myd$KGroups <- cut(myd$K, breaks=c(0, 3, 6, 9, 12), labels=c("(0, 3)", "(3, 6)", "(6, 9)", "(9, 12)"))
# myd<-spam_ham_nb
# myd$KGroups <- cut(myd$K, breaks=c(0, 3, 6, 9, 12), labels=c("(0, 3)", "(3, 6)", "(6, 9)", "(9, 12)"))
# with(myd, aggregate(myd, by=list(KGroups), FUN=summary))

residuals_v_fitted <- function(data) {
    with(data, plot(K, ratio, xlab="K Size", ylab="Ratio (correct/incorrect)"))
    mod<-lm(ratio~K, data=data)
    plot(mod, which=1)
    abline(mod)
}


 # sps <- ggplot(heightweight, aes(x=ageYear, y=heightIn, colour=sex)) +
 #           geom_point() +
 #           scale_colour_brewer(palette="Set1")
 #    sps + geom_smooth()


# raltionship between value of K and ratio
# > with(spam_ham_nb, plot(K, ratio, xlab="k-NN Value of K", ylab="Correct Classification"))
# > mod<-lm(ratio~K, data=spam_ham_nb)
# > abline(mod)

# relationship between training size and ratio
# > mod<-lm(ratio~trainingSize, data=spam_ham_nb)
# > with(spam_ham_nb, plot(trainingSize, ratio, xlab="Training Size (%)", ylab="Correct Classification"))
# > abline(mod)


# display_plot(spam_ham_nb, "Ratio and Training Size" ,spam_ham_nb$trainingSize, spam_ham_nb$ratio, 'TrainingSize', 'Ratio',  'Size of K')