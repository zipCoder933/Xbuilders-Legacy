# To Do
~~1. replace ALL hashmaps and hashets (they are a memory bottleneck)~~

# Do first:
ENtity Opengl mesh rendering
* The reason why rendering the box gets mangled is because the shader wants a vec4 as vertex data, not a vec3.
  * The shader also throws a fit when a vertex array other than vec4 is passed to the mesh
* The Pshader.bind() does other things besides binding the shader. It also sends uniforms every frame
  * This will hurt performance
* The best option will probbably be to make a JOGL shader class from scratch.

# Save for later:
1. **fix issue where the edge of the chunk is invisible because the neighboring chunks havent been loaded yet**
   * Dont ever generate the mesh of a chunk unless it is surrounded by neighbors on all facing sides
2. determine if OpenGl calls can be used in joml
    * it can! https://github.com/processing/processing/wiki/Advanced-OpenGL
4. make block tools more intuitive
5. add dogs

### optimisation:
* try sorting chunks front to back?
* try occlusion culling