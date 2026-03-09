#ifdef GL_ES
precision mediump float;
#endif
uniform sampler2D u_texture;
 
uniform float u_progress;
uniform float u_flashCount;
uniform vec3 u_rgb;

varying vec2 v_texCoords;
varying vec4 v_color;

 void main() {
     // 采样原图
     vec4 texColor = texture2D(u_texture, v_texCoords);
     vec4 finalColor = v_color * texColor;
     
     if(finalColor.a < 0.01) {
     	discard;
     }
     float totalCycle = u_flashCount;
     float phase = fract(u_progress * totalCycle); // 0~1 循环
     // step 生成硬切的白闪
     // phase < 0.5，闪白；>0.5，正常
     float isFlash = step(phase, 0.5);
     // 混合：白闪 = 纯白保留透明度
     finalColor = mix(finalColor, vec4(u_rgb, finalColor.a * 0.8), isFlash);
     gl_FragColor = finalColor;
 }
