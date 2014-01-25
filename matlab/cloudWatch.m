function img_path = cloudWatch( cloud_img)
%% make cloud into one map of one cloud/not cloud

fprintf('Translating clouds to outline...\n'); 
cloud_outline = cloud_to_outline(cloud_img); 




%% read dataset, find closest matches

fprintf('Comparing cloud to dataset...\n'); 
[matches, img_path] = match_outlines(cloud_outline); 


end

