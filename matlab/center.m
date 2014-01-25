function centered_img = center(img, x, y)
    %center image 1: 
    %center x
    if (size(img, 2)/2 - x < 0) 
        %offset is too far right; pad on right
        pad = zeros([size(img, 1), (x - (size(img, 2) - x)), size(img, 3)]); 
        img = horzcat(img, pad); 
    else
        %offset is too far left
        dim = size(img, 2) - x; 
        pad = zeros([size(img, 1), dim - x, size(img, 3)]); 
        img = horzcat(pad, img); 
    end
    %center y
    if (size(img, 1)/2 - y > 0) 
        %center is too far up; pad on top
        pad = zeros([size(img, 1) - y - y, size(img, 2), size(img, 3)]);  
        img = vertcat(pad, img);
    else 
        %center is too far down; pad on bottom
        pad = zeros([y - (size(img, 1) - y), size(img, 2), size(img, 3)]); 
        img = vertcat(img, pad); 
    end 
    centered_img = img; 
end
