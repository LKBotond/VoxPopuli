import React from "react";
import { QueryClientProvider, QueryClient } from "@tanstack/react-query";
import type { View } from "../types/View";
import { VIEWS } from "../types/Constants";
import type { ViewProps } from "../types/Props";
import { IndexView } from "../components/views/IndexView";
import { LoginView } from "../components/views/LoginView";
import { RegistrationView } from "../components/views/RegistrationView";
import { InteriorView } from "../components/views/InteriorView";
import ".././styles/Tailwind.css";
import Main from "../components/generics/Main";

const queryClient= new QueryClient();
function App() {
  const [view, setView] = React.useState<View>(VIEWS.INDEX);
  const handleViewChange = (newView: View) => {
    setView(newView);
  };
  const renderView = () => {
    const viewProps: ViewProps = { changeViewTo: handleViewChange };

    switch (view) {
      case VIEWS.INDEX:
        return <IndexView {...viewProps} />;
      case VIEWS.LOGIN:
        return <LoginView {...viewProps} />;
      case VIEWS.REGISTER:
        return <RegistrationView {...viewProps} />;
      case VIEWS.INTERIOR:
        return <InteriorView {...viewProps} />;
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

export default App;
