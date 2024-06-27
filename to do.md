# Should I just switch to xbuilders 3?
I would have to
* Add vehicles
* add flowing liquid
* fix any bugs with block setter
  ~~* add conversion tool?~~
  ~~* Add repaint and sphere block tools~~
  VS using XB2
* Update to opengl meshes (Major)
* I still have issues with trees looking halved and slower performance in other areas

# To Do
~~1. fix issue where the edge of the chunk is invisible because the neighboring chunks havent been loaded yet~~
   * Dont ever generate the mesh of a chunk unless it is surrounded by neighbors on all facing sides
   * i fixed it, but it causes slower mesh generation
     * Take note if the issue has actually been solved, if it hasnt, scrap the whole thing entirely, and switch back to what I did before...

2. Convert blocks to opengl meshes
**A major issue is that the icon generation now has to be opengl too**
   3. Green meshes are a major problem
   1. change block construction to use custom block buffer
   2. Convert chunk meshes to opengl
   3. Convert block mesh to opengl
   3. Optimize the custom vehicle

See if the lightmap has been set to initialized if the chunk was loaded from the file
We dont need to generate sun for already saved chunks

replace inBoundsOfSLM with  distToPlayer < viewDist

### switching to P3D+LWJGL?
* This would take too much time
* As far as I can tell has no advantages
* It would be just as easy to switch to lwjgl even after i have been using joml for 2 reasons
  * the shader and mesh classes would be easy to swap out or change
  * its all openGL

# Fixed Bugs
### IMPORTANT Because of entity gl rendering, now regular chunks are doubled up, causing a jitter effect on the surface of the mesh
The reason for this is because the p3d meshes are rendered AFTER the opengl meshes