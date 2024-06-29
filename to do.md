# Optimizations
## Hashset and Hashqueue
* These should be used as LITTLE AS POSSIBLE!
  * hashmap/hashset.get() poses a major memory bottleneck

## The main reason why the FPS is stuck at 30fps is because the chunk meshes MUST be replaced with opengl meshes
* Im not going to worry about this, however because I am switching to XBuilders 3

## Occlusion culling is possible for p3d meshes!

## ~~fix issue where the edge of the chunk is invisible because the neighboring chunks havent been loaded yet~~
~~* Dont ever generate the mesh of a chunk unless it is surrounded by neighbors on all facing sides~~

### I fixed it, but it causes slower mesh generation.
Take note if the issue has actually been solved, if it hasnt, scrap the whole thing entirely, and switch back to what I did before...

the only way for me to fix this would be to change the entire terrain updating system to xb3.
* its one of those things that I could do, but it would just take way too much of my time
* additionally, new bugs might be introduced, especially seeing that I use a shader lightmap

**POSSIBLE ALTERNATIVE:** check if the sub chunk is visible from the surface, if so, than fill holes of null or unloaded chunks
  

## ~~Convert blocks to opengl meshes~~
this will take too much time. if i am going to to this, I mind as well switch to XB3.
  
* I will have to load and update the mesh on the main thread
* Use an executor system like in xb3 and only send the mesh to GPU on the main thread
* Is it really worth it?
* Rebuild block shader using entity shader as a template


3. See if the lightmap has been set to initialized if the chunk was loaded from the file
   * We dont need to generate sun for already saved chunks

### switching to P3D+LWJGL?
* This would take too much time
* As far as I can tell has no advantages
* It would be just as easy to switch to lwjgl even after i have been using joml for 2 reasons
  * the shader and mesh classes would be easy to swap out or change
  * its all openGL

# Fixed Bugs
### IMPORTANT Because of entity gl rendering, now regular chunks are doubled up, causing a jitter effect on the surface of the mesh
The reason for this is because the p3d meshes are rendered AFTER the opengl meshes