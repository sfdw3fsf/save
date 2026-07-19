import '@/i18n';
import { MantineProvider } from '@mantine/core';
import { GoogleOAuthProvider } from '@react-oauth/google';
import React from 'react';
import ReactDOM from 'react-dom/client';
import 'react-responsive-carousel/lib/styles/carousel.min.css'; // requires a loader
import App from './App.tsx';
import MySnackBarProvider from './providers/MySnackbarProvider.tsx';
import ReactQueryProvider from './providers/ReactQueryProvider.tsx';
import { GOOGLE_CLIENT_ID } from './utils/constants/data.constant.ts';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ReactQueryProvider>
      <MySnackBarProvider>
        <GoogleOAuthProvider clientId={GOOGLE_CLIENT_ID}>
          <MantineProvider>
            <App />
          </MantineProvider>
        </GoogleOAuthProvider>
      </MySnackBarProvider>
    </ReactQueryProvider>
  </React.StrictMode>,
);
