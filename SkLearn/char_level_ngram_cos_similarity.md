# Char level cos similarity

```python
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.datasets import fetch_20newsgroups`
from sklearn.metrics.pairwise import cosine_similarity
vectorizer = CountVectorizer(analyzer='char', binary=True, ngram_range=(3,3))
vectorizer.fit((x for x in fetch_20newsgroups().data))
cosine_similarity(vectorizer.transform(["hello", "friends"]),vectorizer.transform(["hell", "friendly", "hello my friend"]))
>>> array([[0.81649658, 0.        , 0.48038446],
       [0.        , 0.73029674, 0.49613894]])
```

