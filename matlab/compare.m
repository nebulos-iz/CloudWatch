function [confidence, BW1new, BW2new, colornew] = compare(BW1, BW2, color)
%rotate images to match orientation



[x1, y1, orient1] = orient(BW1);
[x2, y2, orient2] = orient(BW2);

rotate = orient2 - orient1;
BW2 = imrotate(BW2, rotate);
color2 = imrotate(color, rotate);

BW1 = center(BW1, x1, y1); 
BW2 = center(BW2, x2, y2);
color2 = center(color2, x2, y2);

[rows1, cols1] = size(BW1);
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
    BW2 = padarray(BW2, [0 diff_cols/2]);
    color2 = padarray(color2, [diff_cols/2 0]);
end

old_confidence = 0;
BW1new = BW1; 
BW2new = BW2; 
colornew = color2; 

for i = 1:5
    BWsize = size(BW2);
    scale = BWsize * .9;
    
    BW2 = imresize(BW2, scale, 'bilinear');
    color2 = imresize(color2, scale, 'bilinear');
    
    pad = floor((BWsize - size(BW2))/2);

    BW2 = padarray(BW2, pad);
    color2 = padarray(color2, pad);
    
    BW2 = imresize(BW2, size(BW1)); 
    comp = BW2 + BW1;
    right = size(find(comp == 2), 1);
    wrong = size(find(comp == 1), 1); 
    
    confidence = right/wrong;
    if(confidence > old_confidence)
        old_confidence = confidence;
        BW1new = BW1;
        BW2new = BW2;
        colornew = color2;
    end
end

confidence = old_confidence;

end


