import type {HTMLAttributes} from "react";

interface DivProps extends HTMLAttributes<HTMLDivElement> {
  className?: string;
}

function Div({ className = "", ...props }: DivProps) {
  const baseClasses = "font-bold";

  return <div className={`${baseClasses} ${className}`} {...props} />;
}


export default Div;
