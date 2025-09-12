type IconProps = {
    src: string;
    size?: string;
}

function Icon({src, size="1em"}: IconProps) {
    return (
        <img 
            src={src}  
            style={{height: size}}
        />
    );
}

export default Icon