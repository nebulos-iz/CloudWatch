function [matches] = cloudWatch( cloud_img)
%% make cloud into one map of one cloud/not cloud

cloud_outline = cloud_to_outline(cloud_img); 




%% read dataset, find closest matches


[matches] = match_outlines(cloud_outline); 


end

