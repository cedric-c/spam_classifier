# spam_classifier

## Approach

1. Preparing Data
    - Need to create _the table_.
2. ????
3. Classify Emails
4. Stats

## Future Work

- Classification of other types of emails such as phishing emails or critical emails.

## References

### k-Nearest Neighbours (k-NN)

- Jason Brownlee. _K-Nearest Neighbors for Machine Learning_. Accessed on 2019-11-16 at `https://machinelearningmastery.com/k-nearest-neighbors-for-machine-learning/`. <!--  15/04/2018 --> 
    - Entire training dataset is stored.
    - Predictions made by searching entire dataset for K closest instances.
    - Distance measure is used.
        - Euclidean distance: `distance(x, xi) = sqrt( sum( (xj - xij)^2 ) )`
        - Hamming: _pending_
        - Manhattan: _pending_
        - Minkowski: _pending_
        - Tanimoto: _pending_
        - Jaccard: _pending_
        - Mahalanobis: _pending_
        - Cosine: _pending_
    - Tuning of K.
    - A.K.A.: Instance-Based learning, Lazy Learning, Non-Parametric.
    - Can be used for regression and calssification problems.
        - Regression: Prediction based on mean or median of K-most similar instances.
        - Classification: Output calculated as class. 
    - Works well with small number of input variables. More input variables increase input space exponentially. General problem of "Curse of Dimensionality".
    - Preparing Data:
        - Rescale data
        - Lower Dimensionality

- Hardik Jaroli. _K-Nearest Neighbors (KNN) with Python | DataScience+_. Accessed on 2019-11-16 at `https://datascienceplus.com/k-nearest-neighbors-knn-with-python/`. <!--  08/04/2019 -->
    - Pros: simplicity, any number of classes, adding data is easy.
    - Cons: Prediction cost, poor performance with high dimension data, categorical features don't work well.
- Onel Harrison. _Machine Learning Basics with the K-Nearest Neighbors Algorithm_. Accessed on 2019-11-16 at `https://towardsdatascience.com/machine-learning-basics-with-the-k-nearest-neighbors-algorithm-6a6e71d01761`. <!--  10/09/2018 --> 
    - .
- Sumit Dua. _Text Classification using K Nearest Neighbors - Towards Data Science_. Accessed on 2019-11-16 at `https://towardsdatascience.com/text-classification-using-k-nearest-neighbors-46fa8a77acc5`. <!--  12/11/2018 -->  
    - .
