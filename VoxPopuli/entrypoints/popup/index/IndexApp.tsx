import React from "react";
import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import type { View } from "@/entrypoints/common/types/Props";
import { VIEWS } from "@/entrypoints/common/types/Props";
import type { BaseViewProp } from "@/entrypoints/common/types/Props";
import { IndexView } from "./views/IndexView";
import { LoginView } from "./views/LoginView";
import { RegistrationView } from "./views/RegistrationView";
import "@/entrypoints/common/styles/Tailwind.css"
import Main from "@/entrypoints/common/components/generics/Main";

const queryClient= new QueryClient();
function IndexApp() {
  const [view, setView] = React.useState<View>(VIEWS.INDEX);
  const handleViewChange = (newView: View) => {
    setView(newView);
  };
  const renderView = () => {
    const viewProps: BaseViewProp = { changeViewTo: handleViewChange };

    switch (view) {
      case VIEWS.INDEX:
        return <IndexView {...viewProps} />;
      case VIEWS.LOGIN:
        return <LoginView {...viewProps} />;
      case VIEWS.REGISTER:
        return <RegistrationView {...viewProps} />;
      default:
        return <div>Unknown view</div>;
    }
  };

  return (
    <QueryClientProvider client={queryClient}>
      <Main>{renderView()}</Main>
    </QueryClientProvider>
  )
}

export default IndexApp;
