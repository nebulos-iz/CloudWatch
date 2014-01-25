function centered_img = center(img, x, y)
        %center image 1: 
        %center x
        if (size(img, 2)/2 - x < 0) 
            fprintf('right'); 
            %offset is too far right; pad on right
            pad = zeros([size(img, 1), (x - (size(img, 2) - x))]); 
            img = horzcat(img, pad); 
        else
            %offset is too far left
            fprintf('left');
            dim = size(img, 2) - x; 
            pad = zeros([size(img, 1), dim - x]); 
            img = horzcat(pad, img); 
        end
        %center y
        if (size(img, 1)/2 - y > 0) 
            %center is too far down; pad on bottom
            fprintf('down'); 
            dim = size(img, 1) - y; 
            pad = zeros([dim - y, size(img, 2)]); 
            img = vertcat(img, pad);
        else 
            fprintf('up'); 
            %center is too high up; pad on top
            pad = zeros([y - (size(img, 1) - y), size(img, 2)]); 
            img = vertcat(pad, img); 
        end 
        centered_img = img; 
    end
