function [confidence, BW1new, BW2new, colornew] = compare(BW1, BW2, color)
%rotate images to match orientation
[x1, y1, orient1] = orient(BW1);
[x2, y2, orient2] = orient(BW2);

rotate = orient2 - orient1;
BW2 = imrotate(BW2, rotate);
color2 = imrotate(color, rotate);

BW1 = center(BW1, x1, y1); 
BW2 = center(BW2, x2, y2);
color2 = center(color, x2, y2);

[rows1, cols2] = size(BW1);
[rows2, cols2] = size(BW2);

diff_rows = rows2 - rows1;
diff_cols = cols2 - cols1;

if(diff_rows > 0)
    BW1 = padarray(BW1, [diff_rows/2 0]);
else
    diff_rows = 0 - diff_rows;
    BW2 = padarray(BW2, [diff_rows/2 0]);
    color2 = padarray(color2, [diff_rows/2 0]);
end


if(diff_cols > 0)
    BW1 = padarray(BW1, [0 diff_cols/2]);
else
    diff_cols = 0 - diff_cols;
    BW2 = padarray(BW2, [0 diff_cols/2])
    color2 = padarray(color2, [diff_cols/2 0]);
end

comp = BW2 + BW1;
right = find(comp == 2);
wrong = find(comp == 1);

confidence = right/wrong;
BW1new = BW1;
BW2new = BW2;
colornew = color2;

end


