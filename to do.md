# To Do

### fix issue where the edge of the chunk is invisible because the neighboring chunks havent been loaded yet
Dont ever generate the mesh of a chunk unless it is surrounded by neighbors on all facing sides

### Because of entity gl rendering, now regular chunks are doubled up, causing a jitter effect on the surface of the mesh
Simple Solution: render chunks as gl meshes too
* BUT FIRST: it could be that the transparent meah is just being drawn after the GL meshes. try rendering all processing meshes before gl meshes too see if that fixes the peoblem
* if i do GL meahes for chunks i will be in for a massive load of work
  * loading vert data as arraylist and converting to int buffer
  * mesh must be sent to gpu on main thread
  * 

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