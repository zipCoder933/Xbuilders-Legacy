# To Do

### fix issue where the edge of the chunk is invisible because the neighboring chunks havent been loaded yet
Dont ever generate the mesh of a chunk unless it is surrounded by neighbors on all facing sides

### Optimize the custom vehicle
* Switch block mesh to opengl

### switching to P3D+LWJGL?
* This would take too much time
* As far as I can tell has no advantages
* It would be just as easy to switch to lwjgl even after i have been using joml for 2 reasons
  * the shader and mesh classes would be easy to swap out or change
  * its all openGL

# Fixed Bugs
### **IMPORTANT** Because of entity gl rendering, now regular chunks are doubled up, causing a jitter effect on the surface of the mesh
The reason for this is because the p3d meshes are rendered AFTER the opengl meshes