import params
import cv2
import numpy


BLACK = numpy.array([0, 0, 0])
def cloud_to_outline(img_name) :
	im = cv2.imread(img_name, cv2.CV_LOAD_IMAGE_GRAYSCALE)
	orig = im
	#threshold
	im = cv2.GaussianBlur(im, (5, 5), 0)
	#this thresholding works okay, not great. 
	thresh, im = cv2.threshold(im, 0, 255, cv2.THRESH_BINARY+cv2.THRESH_OTSU)
	#crop to square
	print im.shape
	height = im.shape[0]
	width = im.shape[1]
	print type(im)
	if width > height : 
		diff = (width - height)/2
		im = cv2.copyMakeBorder(im, diff, diff, 0, 0, cv2.BORDER_CONSTANT, BLACK)
		orig = cv2.copyMakeBorder(orig, diff, diff, 0, 0, cv2.BORDER_CONSTANT, BLACK)
	elif height > width : 
		diff = (height - width)/2
		im = cv2.copyMakeBorder(im, 0, 0, diff, diff, cv2.BORDER_CONSTANT, BLACK)
		orig = cv2.copyMakeBorder(orig, 0, 0, diff, diff, cv2.BORDER_CONSTANT, BLACK)
	im = cv2.resize(im, (params.cloud_size, params.cloud_size))
	orig = cv2.resize(orig, (params.cloud_size, params.cloud_size))
	return orig, im

	
def main() : 
	for i in range(12) : 
		cloud, outline = cloud_to_outline('../cloud_images/cloud' + str(i) + '.jpg')
		cv2.imshow('cloud', cloud)
		cv2.imshow('outline', outline)
		cv2.waitKey(0)
		cv2.destroyAllWindows()


if __name__ == '__main__' : 
	main()
