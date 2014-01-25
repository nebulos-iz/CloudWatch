function [x,y,angle] = orient(BW)
STATS = regionprops(BW, 'Area', 'Centroid', 'Orientation');
[length] = size(STATS);

orientation = 0; 
center_x = 0; 
center_y = 0;
total_area = bwarea(BW);

for i = 1:length
    center_x = center_x + (STATS(i).Centroid(1)*STATS(i).Area / total_area);
    center_y = center_y + (STATS(i).Centroid(2)*STATS(i).Area / total_area);
    orientation = orientation + (STATS(i).Orientation*STATS(i).Area / total_area);
end

x = floor(center_x);
y = floor(center_y);
angle = orientation;

end