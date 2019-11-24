# Summary Statistics

The following features were selected when conducting the statistics:

1. paragraphs 
    - unbalanced: `Adjusted R-squared:  0.03922`
    - balanced: `Adjusted R-squared:  0.03922`
1. avg_chars_paragraph 
    - unbalanced: `Adjusted R-squared:  0.03013`
    - balanced: `Adjusted R-squared:  0.02599`
1. word_boundary_ratio 
    - unbalanced: `Adjusted R-squared:  0.161`
    - balanced: `Adjusted R-squared:  0.003591`
1. n_word_boundary_ratio 
    - unbalanced: `Adjusted R-squared:  0.00768`
    - balanced: `Adjusted R-squared:  0.007027`
1. whitespace_ratio 
    - unbalanced: `Adjusted R-squared:  0.2396`
    - balanced: `Adjusted R-squared:  0.005835`
1. words 
    - unbalanced: `Adjusted R-squared:  0.009375`
    - balanced: `Adjusted R-squared:  0.04011`
1. max_word_len 
    - unbalanced: `Adjusted R-squared:  0.009331`
    - balanced: `Adjusted R-squared:  0.006839`
1. digits 
    - unbalanced: `Adjusted R-squared:  0.01432`
    - balanced: `Adjusted R-squared:  0.01266`
1. upper_ratio 
    - unbalanced: `Adjusted R-squared:  0.09806`
    - balanced: `Adjusted R-squared:  0.05737`
1. lower_ratio 
    - unbalanced: `Adjusted R-squared:  0.001886`
    - balanced: `Adjusted R-squared:  0.1191`
1. upper_lower_ratio 
    - unbalanced: `Adjusted R-squared:  0.03888`
    - balanced: `Adjusted R-squared:  0.01399`
1. avg_digit_length 
    - unbalanced: `Adjusted R-squared:  0.2961`
    - balanced: `Adjusted R-squared:  0.005906`
1. links 
    - unbalanced: `Adjusted R-squared:  0.2123`
    - balanced: `Adjusted R-squared:  0.3289`
1. class 

## Summaries (unbalanced)

### paragraphs

```
Call:
lm(formula = paragraphs ~ class, data = raw_sh)

Residuals:
   Min     1Q Median     3Q    Max 
 -7.59  -3.60  -1.29   1.40 362.40 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)   7.5967     0.2519   30.16   <2e-16 ***
classspam    -6.2388     0.6025  -10.35   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 11.68 on 2601 degrees of freedom
Multiple R-squared:  0.03959,   Adjusted R-squared:  0.03922 
F-statistic: 107.2 on 1 and 2601 DF,  p-value: < 2.2e-16
```

**********

### avg_chars_paragraph

```
Call:
lm(formula = avg_chars_paragraph ~ class, data = raw_sh)

Residuals:
   Min     1Q Median     3Q    Max 
-184.0  -93.7  -36.7   64.0 5835.7 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)  183.992      4.887  37.651   <2e-16 ***
classspam   -105.736     11.688  -9.046   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 226.5 on 2601 degrees of freedom
Multiple R-squared:  0.0305,    Adjusted R-squared:  0.03013 
F-statistic: 81.84 on 1 and 2601 DF,  p-value: < 2.2e-16
```

### word_boundary_ratio

```
Call:
lm(formula = word_boundary_ratio ~ class, data = raw_sh)

Residuals:
    Min      1Q  Median      3Q     Max 
-2.9596 -0.5566 -0.1511  0.7794 31.0787 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)  2.96123    0.04492   65.92   <2e-16 ***
classspam   -2.40356    0.10745  -22.37   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 2.082 on 2601 degrees of freedom
Multiple R-squared:  0.1614,    Adjusted R-squared:  0.161 
F-statistic: 500.4 on 1 and 2601 DF,  p-value: < 2.2e-16
```

### n_word_boundary_ratio

```
Call:
lm(formula = n_word_boundary_ratio ~ class, data = raw_sh)

Residuals:
   Min     1Q Median     3Q    Max 
 -6.14  -3.11  -0.38   1.73 468.73 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)   6.1418     0.2760  22.256  < 2e-16 ***
classspam    -3.0346     0.6601  -4.597 4.48e-06 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 12.79 on 2601 degrees of freedom
Multiple R-squared:  0.008061,  Adjusted R-squared:  0.00768 
F-statistic: 21.14 on 1 and 2601 DF,  p-value: 4.481e-06
```

### whitespace_ratio

```
Call:
lm(formula = whitespace_ratio ~ class, data = raw_sh)

Residuals:
    Min      1Q  Median      3Q     Max 
-0.8996 -0.1576  0.1573  0.2484  9.0007 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)  0.90267    0.01075   83.97   <2e-16 ***
classspam   -0.73667    0.02571  -28.65   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.4982 on 2601 degrees of freedom
Multiple R-squared:  0.2399,    Adjusted R-squared:  0.2396 
F-statistic: 820.9 on 1 and 2601 DF,  p-value: < 2.2e-16
```

### words

```
Call:
lm(formula = words ~ class, data = raw_sh)

Residuals:
    Min      1Q  Median      3Q     Max 
 -202.6  -167.6   -86.9    21.4 12714.4 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)  202.626      9.553  21.210  < 2e-16 ***
classspam   -115.664     22.850  -5.062 4.44e-07 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 442.8 on 2601 degrees of freedom
Multiple R-squared:  0.009755,  Adjusted R-squared:  0.009375 
F-statistic: 25.62 on 1 and 2601 DF,  p-value: 4.44e-07
```

### max_word_len

```
Call:
lm(formula = max_word_len ~ class, data = raw_sh)

Residuals:
   Min     1Q Median     3Q    Max 
 -82.3  -26.4   -8.3    7.7 8192.7 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)   82.342      4.627   17.79  < 2e-16 ***
classspam    -55.899     11.068   -5.05 4.71e-07 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 214.5 on 2601 degrees of freedom
Multiple R-squared:  0.009711,  Adjusted R-squared:  0.009331 
F-statistic: 25.51 on 1 and 2601 DF,  p-value: 4.713e-07
```

### digits

```
Call:
lm(formula = digits ~ class, data = raw_sh)

Residuals:
   Min     1Q Median     3Q    Max 
-29.23 -15.31  -9.23   6.77 749.77 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)  29.2325     0.9346   31.28  < 2e-16 ***
classspam   -13.9252     2.2353   -6.23 5.44e-10 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 43.31 on 2601 degrees of freedom
Multiple R-squared:  0.0147,    Adjusted R-squared:  0.01432 
F-statistic: 38.81 on 1 and 2601 DF,  p-value: 5.436e-10
```

### upper_ratio

```
Call:
lm(formula = upper_ratio ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.14906 -0.03026 -0.00754  0.01576  0.85094 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept) 0.083109   0.001637   50.78   <2e-16 ***
classspam   0.065954   0.003914   16.85   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.07585 on 2601 degrees of freedom
Multiple R-squared:  0.0984,    Adjusted R-squared:  0.09806 
F-statistic: 283.9 on 1 and 2601 DF,  p-value: < 2.2e-16
```

### lower_ratio

```
Call:
lm(formula = lower_ratio ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.65619 -0.05936 -0.00953  0.05613  0.34381 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept) 0.641587   0.002511 255.555   <2e-16 ***
classspam   0.014607   0.006005   2.433   0.0151 *  
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.1164 on 2601 degrees of freedom
Multiple R-squared:  0.00227,   Adjusted R-squared:  0.001886 
F-statistic: 5.917 on 1 and 2601 DF,  p-value: 0.01506
```

### upper_lower_ratio

```
Call:
lm(formula = upper_lower_ratio ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.11749 -0.04596 -0.01724  0.02988  1.80600 

Coefficients:
             Estimate Std. Error t value Pr(>|t|)    
(Intercept)  0.118138   0.002853   41.41   <2e-16 ***
classspam   -0.070349   0.006824  -10.31   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.1322 on 2601 degrees of freedom
Multiple R-squared:  0.03925,   Adjusted R-squared:  0.03888 
F-statistic: 106.3 on 1 and 2601 DF,  p-value: < 2.2e-16
```

### avg_digit_length

```
Call:
lm(formula = avg_digit_length ~ class, data = raw_sh)

Residuals:
    Min      1Q  Median      3Q     Max 
-1.7082 -0.2261  0.1988  0.4418  2.9918 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)  1.70821    0.01676   101.9   <2e-16 ***
classspam   -1.32665    0.04008   -33.1   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.7766 on 2601 degrees of freedom
Multiple R-squared:  0.2964,    Adjusted R-squared:  0.2961 
F-statistic:  1096 on 1 and 2601 DF,  p-value: < 2.2e-16
```


### links

```
Call:
lm(formula = links ~ class, data = raw_sh)

Residuals:
    Min      1Q  Median      3Q     Max 
-2.8826 -1.8826 -0.1806  2.1174  6.1174 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)  2.88260    0.04263   67.62   <2e-16 ***
classspam   -2.70202    0.10196  -26.50   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 1.976 on 2601 degrees of freedom
Multiple R-squared:  0.2126,    Adjusted R-squared:  0.2123 
F-statistic: 702.2 on 1 and 2601 DF,  p-value: < 2.2e-16
```


## Summary (balanced)

The original summary data was not properly captured. This is the new set of data.

### paragraphs

```
Call:
lm(formula = paragraphs ~ class, data = raw_sh)

Residuals:
   Min     1Q Median     3Q    Max 
 -7.59  -3.60  -1.29   1.40 362.40 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)   7.5967     0.2519   30.16   <2e-16 ***
classspam    -6.2388     0.6025  -10.35   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 11.68 on 2601 degrees of freedom
Multiple R-squared:  0.03959,   Adjusted R-squared:  0.03922 
F-statistic: 107.2 on 1 and 2601 DF,  p-value: < 2.2e-16
```

**********

### avg_chars_paragraph

```
Call:
lm(formula = avg_chars_paragraph ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.03583 -0.02117 -0.00696 -0.00020  0.98573 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept) 0.014274   0.003227   4.424 1.11e-05 ***
classspam   0.021559   0.004563   4.724 2.73e-06 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.06454 on 798 degrees of freedom
Multiple R-squared:  0.02721,   Adjusted R-squared:  0.02599 
F-statistic: 22.32 on 1 and 798 DF,  p-value: 2.728e-06
```

### word_boundary_ratio

```
Call:
lm(formula = word_boundary_ratio ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.00938 -0.00621 -0.00162  0.00002  0.99062 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)  
(Intercept) 0.004284   0.001830   2.341   0.0195 *
classspam   0.005097   0.002588   1.970   0.0492 *
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.0366 on 798 degrees of freedom
Multiple R-squared:  0.004838,  Adjusted R-squared:  0.003591 
F-statistic: 3.879 on 1 and 798 DF,  p-value: 0.04923
```

### n_word_boundary_ratio

```
Call:
lm(formula = n_word_boundary_ratio ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.00771 -0.00726 -0.00033  0.00002  0.99229 

Coefficients:
             Estimate Std. Error t value Pr(>|t|)  
(Intercept) 0.0006588  0.0019334   0.341   0.7334  
classspam   0.0070529  0.0027342   2.580   0.0101 *
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.03867 on 798 degrees of freedom
Multiple R-squared:  0.008269,  Adjusted R-squared:  0.007027 
F-statistic: 6.654 on 1 and 798 DF,  p-value: 0.01007
```

### whitespace_ratio

```
Call:
lm(formula = whitespace_ratio ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.01497 -0.00657 -0.00154  0.00020  0.98503 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept) 0.008435   0.001937   4.354 1.51e-05 ***
classspam   0.006535   0.002740   2.385   0.0173 *  
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.03875 on 798 degrees of freedom
Multiple R-squared:  0.00708,   Adjusted R-squared:  0.005835 
F-statistic:  5.69 on 1 and 798 DF,  p-value: 0.0173
```

### words

```
Call:
lm(formula = words ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.04663 -0.02560 -0.01164  0.00005  0.95337 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept) 0.018481   0.003394   5.445 6.91e-08 ***
classspam   0.028148   0.004800   5.864 6.62e-09 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.06789 on 798 degrees of freedom
Multiple R-squared:  0.04131,   Adjusted R-squared:  0.04011 
F-statistic: 34.38 on 1 and 798 DF,  p-value: 6.621e-09
```

### max_word_len

```
Call:
lm(formula = max_word_len ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.00858 -0.00829 -0.00014 -0.00003  0.99142 

Coefficients:
             Estimate Std. Error t value Pr(>|t|)  
(Intercept) 0.0002705  0.0023038   0.117    0.907  
classspam   0.0083075  0.0032581   2.550    0.011 *
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.04608 on 798 degrees of freedom
Multiple R-squared:  0.008082,  Adjusted R-squared:  0.006839 
F-statistic: 6.502 on 1 and 798 DF,  p-value: 0.01096
```

### digits

```
Call:
lm(formula = digits ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.01457 -0.01247 -0.00059 -0.00007  0.98543 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept) 0.001223   0.002814   0.434 0.664097    
classspam   0.013346   0.003980   3.353 0.000836 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.05628 on 798 degrees of freedom
Multiple R-squared:  0.0139,    Adjusted R-squared:  0.01266 
F-statistic: 11.25 on 1 and 798 DF,  p-value: 0.0008358
```

### upper_ratio

```
Call:
lm(formula = upper_ratio ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.15401 -0.06927 -0.01963  0.02340  0.84599 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept) 0.093220   0.006102  15.277  < 2e-16 ***
classspam   0.060792   0.008630   7.044 4.02e-12 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.122 on 798 degrees of freedom
Multiple R-squared:  0.05855,   Adjusted R-squared:  0.05737 
F-statistic: 49.62 on 1 and 798 DF,  p-value: 4.024e-12
```

### lower_ratio

```
Call:
lm(formula = lower_ratio ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.67582 -0.07476  0.00597  0.08308  0.32418 

Coefficients:
             Estimate Std. Error t value Pr(>|t|)    
(Intercept)  0.778250   0.006937  112.19   <2e-16 ***
classspam   -0.102431   0.009810  -10.44   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.1387 on 798 degrees of freedom
Multiple R-squared:  0.1202,    Adjusted R-squared:  0.1191 
F-statistic:   109 on 1 and 798 DF,  p-value: < 2.2e-16
```

### upper_lower_ratio

```
Call:
lm(formula = upper_lower_ratio ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.01888 -0.01262 -0.00264  0.00089  0.98112 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept) 0.006447   0.002503   2.576 0.010180 *  
classspam   0.012433   0.003540   3.513 0.000469 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.05006 on 798 degrees of freedom
Multiple R-squared:  0.01523,   Adjusted R-squared:  0.01399 
F-statistic: 12.34 on 1 and 798 DF,  p-value: 0.0004688
```

### avg_digit_length

```
Call:
lm(formula = avg_digit_length ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.16171 -0.03600 -0.00463  0.03006  0.83829 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept) 0.147484   0.004198  35.134   <2e-16 ***
classspam   0.014231   0.005937   2.397   0.0167 *  
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.08396 on 798 degrees of freedom
Multiple R-squared:  0.00715,   Adjusted R-squared:  0.005906 
F-statistic: 5.747 on 1 and 798 DF,  p-value: 0.01675
```


### links

```
Call:
lm(formula = links ~ class, data = raw_sh)

Residuals:
     Min       1Q   Median       3Q      Max 
-0.39958 -0.03792 -0.03792  0.26708  0.62875 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)  0.39958    0.01291   30.96   <2e-16 ***
classspam   -0.36167    0.01825  -19.82   <2e-16 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 0.2581 on 798 degrees of freedom
Multiple R-squared:  0.3298,    Adjusted R-squared:  0.3289 
F-statistic: 392.6 on 1 and 798 DF,  p-value: < 2.2e-16
```


## Conclusions

- None yet. Likely confounding variables.


