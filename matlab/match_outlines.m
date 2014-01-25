function [match] = match_outlines(cloud_outline)

% addpath(genpath('~/Documents/MATLAB/LabelMeToolbox')); 
% 
% HOMEIMAGES = '/home/emily/Documents/MATLAB/LabelMe/Images';
% HOMEANNOTATIONS = '/home/emily/Documents/MATLAB/LabelMe/Annotations';
% LMinstall (HOMEIMAGES, HOMEANNOTATIONS);
% 
% D = LMDatabase(HOMEANNOTATIONS);

path = '/home/emily/Documents/MATLAB/1obj/';

files = dir(path);
counter = 1;
confidences = []; 
clouds = {}; 
objs = {}; 
colors = {}; 
names = {}; 

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
    color_dir= dir(strcat(folder_name, '/src_color/*.png')); 
    color_img = imread(strcat(folder_name, '/src_color/', color_dir.name)); 
    
    mask = image(:, :, 1) == 255 & image(:, :, 2) == 0 & image(:, :, 3) == 0;
    
    [confidence, cloud_new, obj_new, color_new] = compare(cloud_outline, mask, color_img); 
    
    
    names{counter} = name; 
    confidences(counter) = confidence; 
    clouds{counter} = cloud_new; 
    objs{counter} = obj_new; 
    colors{counter} = color_new; 
    
    counter = counter+1; 
end



[B, IX] = sort(confidences, 'descend');

conf = confidences(IX(1))
figure(1); 
imshow(clouds{IX(1)}); 
figure(2); 
imshow(objs{IX(1)}); 
figure(3); 
imshow(colors{IX(1)}); 

match = colors{IX(1)}; 

end 