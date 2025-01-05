'use client';

import React from 'react';

interface ClientProviderProps {
  children: React.ReactNode;
}

const ClientProvider: React.FC<ClientProviderProps> = ({ children }) => {
  return <>{children}</>;
};

export default ClientProvider; 