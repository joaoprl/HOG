
import sys
import Image
import ImageDraw
import math
import operator

def getHistograms(image, (w,h)):
    histograms = {}
    for y in range(0, h):
        for x in range(0, w):
            if x == 0 or x == w - 1 or y == 0 or y == h - 1:
                x_vec = 0
                y_vec = 0
            else:
                x_vec = image[x + 1, y] - image[x - 1, y]
                y_vec = image[x, y + 1] - image[x, y - 1]

            v = pow(x_vec, 2) + pow(y_vec, 2)            
            mag = pow( v, 0.5)
            ang = math.atan2(y_vec, x_vec)

            angRad = math.degrees(ang)
            
            histogram = histograms.get((x//8, y//8), 9 * [0])
            pos1 = ((x + 10) // 20 - 1) % 9
            pos2 = ((x + 10) // 20) % 9
           
            histogram[pos1] += ((20 - ((x % 10.0) % 20)) / 20) * mag
            histogram[pos2] += (1 - (20 - ((x % 10.0) % 20)) / 20) * mag
              
            histograms.update({(x//8, y//8) : histogram})

    return histograms
    

def createOutput(histograms, (w, h)):

    output = []

    for y in range(0, h // 8 - 1):
        for x in range(0, w // 8 - 1):

            block = histograms.get((x,y)) \
               + histograms.get((x + 1, y)) \
               + histograms.get((x, y + 1)) \
               + histograms.get((x + 1, y + 1))

            mag = 0
            for i in range(0, len(block)):
                mag += block[i]
            if mag != 0:
                for i in range(0, len(block)):
                    block[i] /= mag
            output += block

    return output
    
def main():

    img = Image.open(sys.argv[1])
    img_grey = img.convert('L') # convert the image to greyscale
    pix_grey = img_grey.load() # pix[x,y] = (r,g,b)
    draw_grey = ImageDraw.Draw(img_grey)

    histograms = getHistograms(pix_grey, img_grey.size)
    output = createOutput(histograms, img_grey.size)
    
    print len(output)
    for i in range(0, len(output)):
        print output[i]
    
    return 0

if __name__ == "__main__":
    main()    
