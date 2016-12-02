from PIL.Image import *
from PIL.ImageOps import mirror
import os

for path, dirs, filelist in os.walk("."):
	for file in filelist:
		if (file[-3:]=="png"):
			p = '/'.join(path.split('\\'))+'/'
			img = open(p+file)
			(largeur, hauteur) = img.size
			if (largeur == 1024):
				img = img.resize((512,512), ANTIALIAS)
				img.save(p+file)

