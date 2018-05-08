# Fuzzy matching by char level cosine similarity

This is an faster way to compare ngram instead of using `pip install ngram`

```python
documents=["hello", "friends", "New York"]
query=["hello, goodbye", "friendly", "hello my friend", "new york jets"]
```

The above are the documents that we want to index and the query that search the indexed documents.

```python
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.datasets import fetch_20newsgroups
from sklearn.metrics.pairwise import cosine_similarity

documents=["hello", "friends", "New York"]
query=["hello, goodbye", "friendly", "hello my friend", "new york jets"]
import numpy as np

vectorizer = CountVectorizer(analyzer='char', binary=True, ngram_range=(3,3))
vectorizer.fit((x for x in fetch_20newsgroups().data))
cos=cosine_similarity(vectorizer.transform(query), vectorizer.transform(documents))
print(cos)
print(np.take(documents, np.argmax(cos, axis=1)))

[[0.5        0.         0.        ]
 [0.         0.73029674 0.        ]
 [0.48038446 0.49613894 0.        ]
 [0.         0.         0.73854895]]

array(['hello', 'friends', 'friends', 'New York'], dtype='<U8')
```

