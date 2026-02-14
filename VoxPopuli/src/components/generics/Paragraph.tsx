import React from "react";

interface PProps extends React.HTMLAttributes<HTMLParagraphElement> {
  className?: string;
}

function P({ className = "", ...props }: PProps) {
  const baseClasses = "w-full m-1 px-4 py-2 rounded text-white border";

  return <p className={`${baseClasses} ${className}`} {...props} />;
}

export default P;