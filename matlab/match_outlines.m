function [matches] = match_outlines(cloud_outline)

% addpath(genpath('~/Documents/MATLAB/LabelMeToolbox')); 
% 
% HOMEIMAGES = '/home/emily/Documents/MATLAB/LabelMe/Images';
% HOMEANNOTATIONS = '/home/emily/Documents/MATLAB/LabelMe/Annotations';
% LMinstall (HOMEIMAGES, HOMEANNOTATIONS);
% 
% D = LMDatabase(HOMEANNOTATIONS);

path = '/home/emily/Documents/MATLAB/1obj/';

files = dir(path)

confidences = zeros([100]);
counter = 1;

for folder = files'
    %file random folder name
    if (folder.name(1) == '.') 
        continue; 
    end
    folder_name = strcat(path, folder.name); 
    name_file = dir(strcat(folder_name, '/*.name'));
    name = name_file.name;
    imgs = dir(strcat(folder_name, '/human_seg/*.png')); 
    image = imread(strcat(folder_name, '/human_seg/', imgs(1).name));
    imshow(image); 
    
    red = image(:, :,1); 
    
    mask = image(:, :, 1) == 255 & image(:, :, 2) == 0 & image(:, :, 3) == 0;
    imshow(mask);
 
end


end 