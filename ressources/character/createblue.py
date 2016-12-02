from PIL.Image import *
from PIL.ImageOps import mirror
import os

dirlist = list()

animations = ["01idle","02walk","03run","04jump","05slashjump","06slash","07hurt","08death"]

for anim in animations:
    dirlist.append([anim+"/"+f for f in os.listdir(anim)])
    if not os.path.exists("./red/"+anim):
        os.makedirs("./red/"+anim)
    if not os.path.exists("./blue/"+anim):
        os.makedirs("./blue/"+anim)

for d in dirlist:
    for f in d:
        img = open("./"+f)
        (largeur, hauteur) = img.size
        newImg = mirror(img)

        for x in range(largeur):
            for y in range(hauteur):
                (r,v,b,a) = newImg.getpixel((x,y))
                if (r>=100 and v<=155):
                    newImg.putpixel((x,y), (b,v,r,a))
        img.save("./red/"+f)
        newImg.save("./blue/"+f)
