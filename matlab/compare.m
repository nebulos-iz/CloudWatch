function [confidence] = compare(BW1, x1, y1, orient, BW2, x2 ,y2, orient2)

rotate = orient2 - orient1;
BW2 = imrotate(BW2, rotate);
diff_x = x2 - x1;
diff_y = y2 - y1;

BW2 = padarray(BW2, [diff_y, diff_x]);

BW2 = BW2(

end