import { Route, Routes } from 'react-router-dom';
import Overlay from "./Overlay.tsx";
import MainPage from "./MainPage.tsx";

function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<Overlay />}>
                <Route path="main" element={<MainPage />}/>
            </Route>
        </Routes>
    )
}

export default AppRoutes;