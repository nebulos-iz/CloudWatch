function [confidence] = compare(BW1, x1, y1, orient1, BW2, x2 ,y2, orient2)
%rotate images to match orientation
rotate = orient2 - orient1;
BW2 = imrotate(BW2, rotate);

t = [1:10]
y = center(t, 2, 4)

BW1 = center(BW1, x1, y1); 
BW2 = center(BW2, x2, y2); 

t = magic(10); 
y = center(t, 2, 4)



    function centered_img = center(img, x, y)
        %center image 1: 
        %center x
        if (size(img, 2)/2 - x > 0) 
            %offset is too far right; pad on right
            pad = zeros([size(img, 1), (x - (size(img, 2) - x))]); 
            img = horzcat(img, pad); 
        else
            %offset is too far left
            dim = size(img, 2) - x; 
            pad = zeros([size(img, 1), dim - x]); 
            img = horzcat(pad, img); 
        end
        %center y
        if (size(img, 1)/2 - y > 0) 
            %center is too far down; pad on bottom
            dim = size(img, 1) - y; 
            pad = zeros([dim - y, size(img, 2)]); 
            img = vertcat(img, pad);
        else 
            %center is too high up; pad on top
            pad = zeros([y - (size(img, 1) - y), size(img, 2)]); 
            img = vertcat(pad, img); 
        end 
        centered_img = img; 
    end



end