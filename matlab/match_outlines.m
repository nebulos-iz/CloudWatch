function [matches] = match_outlines(cloud_outline)

% addpath(genpath('~/Documents/MATLAB/LabelMeToolbox')); 
% 
% HOMEIMAGES = '/home/emily/Documents/MATLAB/LabelMe/Images';
% HOMEANNOTATIONS = '/home/emily/Documents/MATLAB/LabelMe/Annotations';
% LMinstall (HOMEIMAGES, HOMEANNOTATIONS);
% 
% D = LMDatabase(HOMEANNOTATIONS);

path = '/home/emily/Documents/MATLAB/1obj/';

files = dir(path);

confidences = zeros([100]);
counter = 1;

for file = files'
    fir = dir(file.name);
    image = imread(fir(1).name);
    red = image(:,1);
    mask = im2bw(red, 1);
    
    [row_size, cols_size, ~] = size(mask);
    if(row_size/col_size > 1.1)
        mask = imcrop(mask, [0, 0, col_size, col_size]);
    elseif(row_size/col_size < .9)
        mask = imcrop(mask, [0, 0, row_size, row_size]);
    end
    mask = imresize(mask, [300, 300]);
    
    counter = counter +1;
end


end 