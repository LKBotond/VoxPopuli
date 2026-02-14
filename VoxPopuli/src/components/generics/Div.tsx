import React from "react";

interface DivProps extends React.HTMLAttributes<HTMLDivElement> {
  className?: string;
}

function Div({ className = "", ...props }: DivProps) {
  const baseClasses = "font-bold";

  return <div className={`${baseClasses} ${className}`} {...props} />;
}


export default Div;
