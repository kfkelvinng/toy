# Image N-Dimension
## Counting unique pixel in img
```
img = cv2.imread(...)
flatten_px = img.reshape((-1, 3))
np.unique(flatten_px, axis=0, return_counts=True)
```

# Top 100
np.take(kl, kl_tfidf_sum.argsort())[:100]
