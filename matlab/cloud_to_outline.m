function [fin_image] = cloud_to_outline(orig_image)
orig_image = imread(orig_image);
[row_size, col_size, ~] = size(orig_image);

toPlayWith = .1;

orig_image = im2bw(orig_image, mean2(rgb2gray(orig_image))/256 + toPlayWith);

% orig_image = rgb2gray(orig_image);

if(row_size/col_size > 1.1)
    orig_image = imcrop(orig_image, [0, 0, col_size, col_size]);
elseif(row_size/col_size < .9)
    orig_image = imcrop(orig_image, [0, 0, row_size, row_size]);
end
orig_image = imresize(orig_image, [300, 300]);

fin_image = orig_image;

%fin_image = max_suppr(orig_image);



% mask = zeros(size(orig_image));
% mask(10:end-10,10:end-10) = 1;
% 
% fin_image = activecontour(orig_image, mask, 300);
% 
% figure, imshow(fin_image);
% title('fig2');
end