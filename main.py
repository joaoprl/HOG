
import sys
import Image
import ImageDraw
import math

def getGradient(image, (x,y), (w,h)):
    x_gradient = image[x + 1, y] - image[x - 1, y]
    y_gradient = image[x, y + 1] - image[x, y - 1]
    gradients = {}
    for x in range(1, w - 1):
        for y in range(1, h - 1):
            x_vec = image[x + 1, y] - image[x - 1, y]
            y_vec = image[x, y + 1] - image[x, y - 1]

            v = pow(x_vec, 2) + pow(y_vec, 2)            
            mag = pow( v, 0.5)
            ang = math.atan2(y_vec, x_vec)

            gradients[x, y] = (mag, ang)
            
def main():
    img = Image.open("penguin.jpg")
    img = img.convert("RGBA")
    pix = img.load() # pix[x,y] = (r,g,b)
    draw = ImageDraw.Draw(img)    

    width,height = img.size

    img_grey = img.convert('L') # convert the image to greyscale
    pix_grey = img_grey.load()
    draw_grey = ImageDraw.Draw(img_grey)    

    for i in xrange(7, width, 8):
        draw_grey.line((i,0,i,height))
    
    for i in xrange(7, height, 8):
        draw_grey.line((0,i,width,i))
    
    draw.line((0,0,100,100), fill=(0,0,0,0), width=2)
    img_grey.show()

    getGradient(pix_grey, (100,100), (width, height))
    
    return 0


if __name__ == "__main__":
    main()    
