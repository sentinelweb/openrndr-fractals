// from https://www.shadertoy.com/view/NdSGRG
#define ITR 1000

float julia(vec2 uv){
    int j = 0;
    for(int i=0;i<ITR;i++){
        j++;
        vec2 c = vec2(p_x, p_y );
        vec2 d = vec2(p_time * p_speed, 0.0);
        uv = vec2(uv.x * uv.x - uv.y * uv.y, 2.0 * uv.x * uv.y) + c + d;
        if(length(uv) > float(ITR)){
            break;
        }
    }
    return float(j) / float(ITR)*10.0; // todo spike the color bounds somehow to increase contrast
}
//----- main -------------
//vec2 uv=(2.0*fragCoord.xy-iResolution.xy)/iResolution.y;
float aspect = c_boundsSize.x / c_boundsSize.y;
vec2 uv = vec2(c_boundsPosition.x * aspect - .85, c_boundsPosition.y - .5);
uv *= 2.0;
//uv *= abs(sin(iTime*0.2));
float f = julia(uv);

x_fill = vec4(vec3(f), 1.0);
