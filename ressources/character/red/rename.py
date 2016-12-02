from PIL.Image import Image
import os
import glob

# files = os.listdir(".")
files = glob.glob("*.png")

for f in files:
    os.rename(f, f[-8:])

files = [f[-8:] for f in files]

# for f in files:
#     img = Image.open(f)
#     (h, w) = img.size
#     if (h == 1024):
#         img = img.resize((512,512), Image.ANTIALIAS)


