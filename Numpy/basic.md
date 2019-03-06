*Counting unique pixel in img*
```
img = cv2.imread(...)
flatten_px = img.reshape((-1, 3))
np.unique(flatten_px, axis=0, return_counts=True)
```

