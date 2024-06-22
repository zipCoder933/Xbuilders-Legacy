# To Do

Most important: **fix issue where the edge of the chunk is invisible because the neighboring chunks havent been loaded yet**
   * Dont ever generate the mesh of a chunk unless it is surrounded by neighbors on all facing sides

### optimisation:
* try sorting chunks front to back?
* try occlusion culling

### Using opengl meshes for entities
* We dont have to worry about shaders as long as we only bind the shader once per frame
* Get the JOGLMeshDemo working with a PShader
  If you need, reverse engineer JOGLDEmoMod further

### switching to P3D+LWJGL?
* This would take too much time
* As far as I can tell has no advantages
* It would be just as easy to switch to lwjgl even after i have been using joml for 2 reasons
  * the shader and mesh classes would be easy to swap out or change
  * its all openGL