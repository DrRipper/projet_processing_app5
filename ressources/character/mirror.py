from PIL.Image import *
from PIL.ImageOps import mirror
import os

for path, dirs, filelist in os.walk("."):
	for file in filelist:
		if (file[-3:]=="png"):
			tmp = path.split('\\')
			p = '/'.join(tmp)+'/'
			color, anim = tmp[1], tmp[2]
			if (anim[0]!='m'):
				img = open(p+file)
				img = mirror(img)
				mirrorpath = "./"+color+"/m"+anim
				if not os.path.exists(mirrorpath):
					os.makedirs(mirrorpath)
				img.save("./"+color+"/m"+anim+"/"+file)

			
