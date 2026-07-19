'use client';
import { MANAGER_USER_CONTENT } from '@/utils/enums/manager/manager.enum';
import { useState } from 'react';
import UserList from './components/UserList';
import UserResignedList from './components/UserResignedList';

const page = () => {
  const [content, setContent] = useState<MANAGER_USER_CONTENT>(MANAGER_USER_CONTENT.USER_LIST);
  return content === MANAGER_USER_CONTENT.USER_LIST ? (
    <UserList
      onSetContent={() => {
        setContent(MANAGER_USER_CONTENT.RESIGNED_LIST);
      }}
    />
  ) : (
    <UserResignedList
      onSetContent={() => {
        setContent(MANAGER_USER_CONTENT.USER_LIST);
      }}
    />
  );
};

export default page;
