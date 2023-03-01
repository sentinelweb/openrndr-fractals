class KochFrags {
    val funs = """
vec2 N(float angle) {
    return vec2(sin(angle), cos(angle));
}
"""

    val shader = """
// KIFS fractal - kaleidoscopic IFS
// can do ferns, trees, etc


    // Normalized pixel coordinates (from 0 to 1)
    //vec2 uv = (c_boundsPosition.xy - .5 * c_boundsSize.xy) / c_boundsSize.y;
    vec2 uv = c_boundsPosition.xy - .5;
    //vec2 mouse = iMouse.xy/c_boundsSize.xy;
    
    uv *= 1.25;
    
    // Time varying pixel color
    vec3 col = vec3(0);
    
    
    uv.x = abs(uv.x);//reflects around the y axis
    //float angle = mouse.x* 3.1415;
    //float mouse_angle = mouse.x*3.14157; // mouse deg
    float koch_angle = (2./3.)*3.14157; // 60 deg
    float reflect_angle = 5./6.*3.14157; // transpose curve deg
    //float koch_angle = (1./4.)*3.14157; // 60 deg
    //float reflect_angle = 3./4.*3.14157; // transpose curve deg
    
    // shift up whole snowflake
    uv.y += tan(reflect_angle)*.5;
    
    vec2 n = N(reflect_angle);
    float d =  dot(uv - vec2(.5, 0), n);
    uv -= n * max(0., d) * 2.;

    // col += smoothstep(.01, .0, abs(d));

    n = N(koch_angle);
    float scale = 1.;
    uv.x += .5; // 1.5/3
    for (int i=0;i<p_scale;i++) {
    
        // transform uv to reset inside segment
        uv *= 3.;
        scale *= 3.;
        uv.x -= 1.5;
        
        uv.x = abs(uv.x);
        uv.x -= .5;

        uv -= n * min(0., dot(uv, n)) * 2.;
    }
    
    float d_len = length(uv - vec2(clamp(uv.x, -1.,1.),0.));
    col += smoothstep(1./c_boundsSize.y, .0, d_len/scale);
    
    // space is folded into the snowflake
    
    // show uv transformation
    // col.rg += uv/scale;
    
    // can use texture to fold instead
    uv /= scale;
    vec2 texOffset = vec2(p_tex_x, p_tex_y);
    
    col += texture(p_image, texOffset + uv * 5. + sin(p_time*1) * .4).rgb;
    
    // Output to screen
    x_fill = vec4(col, 1.0);
//}
"""
}