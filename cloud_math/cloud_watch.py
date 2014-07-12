import params
import cv2
import numpy as np


BLACK = np.array([0, 0, 0])
def cloud_to_outline(img_name) :
	im = cv2.imread(img_name, cv2.CV_LOAD_IMAGE_GRAYSCALE)
	orig = im
	#threshold
	im = cv2.GaussianBlur(im, (5, 5), 0)
	#this thresholding works okay, not great. 
	thresh, im = cv2.threshold(im, 0, 255, cv2.THRESH_BINARY+cv2.THRESH_OTSU)
	#crop to square
	height = im.shape[0]
	width = im.shape[1]
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

def red_mask_to_outline(img_name) : 
	# probably going to convert everything to outlines / thresholds and work from there. 
	im = cv2.imread(img_name)
	red = np.array([0, 0, 255], dtype=np.uint8)
	mask = cv2.inRange(im, red, red)
	return mask

def get_color_distribution(image) :
	colors = set()
	for c in image : 
		for r in c : 
			tup = (r[0], r[1], r[2])
			colors.add(tup)
	return colors

def imread(image_name) : 
	im = cv2.imread(image_name)
	im.flags.writeable = False
	return im


def main() : 

def test_get_color_distribution() : 
	s = get_color_distribution(cv2.imread("../training/weizmann_seg_db/1obj/chaom38/human_seg/chaom38_13.png"))

def test_red_mask_to_outline() : 
	img_path = "../training/weizmann_seg_db/1obj/chaom38/human_seg/chaom38_13.png"
	img_path = "../training/shapes/star.jpg"
	mask = red_mask_to_outline(img_path)
	m = cv2.imread(img_path)
	cv2.imshow('mask', mask)
	#cv2.imshow('original', m)
	cv2.waitKey(0)
	cv2.destroyAllWindows()
	
def test_cloud_to_outline() : 
	for i in range(12) : 
		cloud, outline = cloud_to_outline('../cloud_images/cloud' + str(i) + '.jpg')
		cv2.imshow('cloud', cloud)
		cv2.imshow('outline', outline)
		cv2.waitKey(0)
		cv2.destroyAllWindows()


if __name__ == '__main__' : 
	main()
