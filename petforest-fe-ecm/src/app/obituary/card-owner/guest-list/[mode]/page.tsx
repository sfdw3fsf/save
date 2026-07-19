'use client';
import { ChevronLeftIcon } from '@chakra-ui/icons';
import { useParams, useRouter, useSearchParams } from 'next/navigation';

import {
  createObituaryMessageApi,
  deleteObituaryMessageApi,
  getObituaryMessageDetailApi,
  obituaryMessagesKeys,
  updateObituaryMessageApi,
  validateObituaryMessageApi,
} from '@/store/obituary/obituary.api';
import { obituaryMessagesRespToDto } from '@/store/obituary/obituary.service';
import { ObituaryMessageCreateREQ, ObituaryMessageValidateREQ } from '@/store/obituary/request/obituary.request';
import { orString } from '@/utils/parser/io.parser';
import { keepPreviousData, useMutation, useQuery } from '@tanstack/react-query';
import { Input } from 'antd';
import TextArea from 'antd/es/input/TextArea';
import { useEffect, useMemo, useRef, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import '../../../obituary.css';
import CreateSuccessModal from './components/CreateSuccessModal';
import DeleteModal from './components/DeleteModal';
import EditModal from './components/EditModal';
import SuccessModal from './components/SuccessModal';

const page = () => {
  const searchParams = useSearchParams();
  const router = useRouter();
  const { mode } = useParams();
  const { control, handleSubmit, reset, getValues, watch } = useForm<ObituaryMessageCreateREQ>();
  const previousDataRef = useRef<ObituaryMessageCreateREQ | null>(null);
  const validatedPassword = useRef<string>('');

  const [isOpenValidate, setIsOpenValidate] = useState<'delete' | 'edit'>();
  const [isOpenDelete, setIsOpenDelete] = useState<boolean>(false);
  const [isOpenSuccess, setIsOpenSuccess] = useState<boolean>(false);
  const [isCreateSuccess, setIsCreateSuccess] = useState<boolean>(false);

  const contentWatch = watch('content');

  const id: string = useMemo(() => {
    return orString(searchParams.get('id'));
  }, [searchParams]);

  const messageId: number = useMemo(() => {
    return Number(searchParams.get('messageId'));
  }, [searchParams]);

  const { mutate: mutateCreateObituaryMessage } = useMutation({
    mutationFn: ({ id, body }: { id: string; body: ObituaryMessageCreateREQ }) => createObituaryMessageApi(id, body),
    onSuccess: (resp) => {
      setIsCreateSuccess(true);
    },
    onError(error) {
      alert(((error as any).response.data.message as string[]).map((item) => item));
    },
  });

  const { mutate: mutateUpdateObituaryMessage } = useMutation({
    mutationFn: ({ id, messageId, body }: { id: string; messageId: number; body: ObituaryMessageCreateREQ }) =>
      updateObituaryMessageApi(id, messageId, body),
    onSuccess: () => {
      setIsOpenSuccess(true);
      setIsOpenValidate(undefined);
    },
    onError(error) {
      alert(((error as any).response.data.message as string[]).map((item) => item));
    },
  });

  const { mutate: mutateUpdateObituaryValidateMessage } = useMutation({
    mutationFn: ({ id, messageId, body }: { id: string; messageId: number; body: ObituaryMessageValidateREQ }) =>
      validateObituaryMessageApi(id, messageId, body),
    onSuccess: () => {
      const body = getValues();
      onUpdate(body);
    },
    onError(error) {
      alert('비밀번호가 틀렸습니다.');
    },
  });

  const { mutate: mutateDeleteObituaryMessage } = useMutation({
    mutationFn: ({ id, messageId, params }: { id: string; messageId: number; params: ObituaryMessageValidateREQ }) =>
      deleteObituaryMessageApi(id, messageId, params),
    onSuccess: () => {
      setIsOpenSuccess(true);
      setIsOpenDelete(false);
      setIsOpenValidate(undefined);
    },
    onError(error) {},
  });

  const { data: obituaryMessageData } = useQuery({
    queryKey: obituaryMessagesKeys.detail(id),
    queryFn: () => getObituaryMessageDetailApi(id, messageId),
    select: (response) => {
      return obituaryMessagesRespToDto(response.data.data);
    },
    placeholderData: keepPreviousData,
    enabled: !!id && !!messageId,
  });

  const onValidatePassword = (password: string) => {
    validatedPassword.current = password;
    mutateUpdateObituaryValidateMessage({ id, messageId, body: { password } });
  };

  const onUpdate = (values: ObituaryMessageCreateREQ) => {
    if (mode === 'create' && id) {
      mutateCreateObituaryMessage({ id, body: values });
    } else {
      if (isOpenValidate === 'edit') {
        mutateUpdateObituaryMessage({
          id,
          messageId,
          body: { author: values.author, content: values.content, password: validatedPassword.current },
        });
      }
      if (isOpenValidate === 'delete') {
        setIsOpenDelete(true);
      }
    }
  };

  const onDelete = () => {
    mutateDeleteObituaryMessage({ id, messageId, params: { password: validatedPassword.current } });
  };

  useEffect(() => {
    if (!obituaryMessageData) return;

    if (JSON.stringify(previousDataRef.current) !== JSON.stringify(obituaryMessageData)) {
      reset(obituaryMessageData);
      previousDataRef.current = obituaryMessageData;
    }
  }, [obituaryMessageData, reset]);

  return (
    <div className='flex flex-col text-center items-center px'>
      <div className='flex items-center w-full relative bg-grey-pearl py-6'>
        <button
          className='absolute left-[20px] top-2/4 -translate-y-2/4'
          onClick={() => {
            router.back();
          }}>
          <ChevronLeftIcon w={7} h={7} />
        </button>

        <div className=' w-full text-[17px] font-bold '>방명록 작성</div>
      </div>
      <div className='w-full max-w-[480px] mt-[15px]'>
        <div className='flex items-center gap-3'>
          <span className='text-[15px]'>작성자</span>
          <Controller
            name='author'
            control={control}
            render={({ field: { onChange, value } }) => (
              <Input
                value={value}
                onChange={onChange}
                type='text'
                className='input font-normal text-[14px] flex-1'
                placeholder='이름을 입력 해주세요.'
              />
            )}
          />
        </div>
        <div className='relative mt-2.5 w-full h-[300px] border border-[#bfcdc4] rounded-lg overflow-hidden p-[20px_20px_30px]'>
          <Controller
            name='content'
            control={control}
            render={({ field: { onChange, value } }) => (
              <TextArea
                value={value}
                onChange={onChange}
                className='!w-full !h-full focus:!outline-transparent !border-0 !resize-none'
                maxLength={2000}
              />
            )}
          />

          <span className='absolute right-3 bottom-3'>{contentWatch?.length || 0}/2000</span>
        </div>

        {mode === 'create' && (
          <div className='flex items-center gap-3 mt-2.5'>
            <span className='text-[15px]'>비밀번호</span>
            <Controller
              name='password'
              control={control}
              render={({ field: { onChange, value } }) => (
                <Input
                  value={value}
                  onChange={onChange}
                  type='text'
                  className='input font-normal text-[14px] flex-1'
                  placeholder='방명록 삭제/수정시 필요 합니다.'
                />
              )}
            />
          </div>
        )}

        <div className='flex gap-3'>
          {mode !== 'create' ? (
            <>
              <button
                onClick={() => {
                  setIsOpenValidate('edit');
                }}
                className='btn-primary-obituary mt-[35px]'>
                수정
              </button>
              <button
                onClick={() => {
                  setIsOpenValidate('delete');
                }}
                className='btn-primary-obituary btn-outlined-obituary !border-[1px] !border-[#0d4221] mt-[35px]'>
                삭제
              </button>
            </>
          ) : (
            <button onClick={handleSubmit(onUpdate)} className='btn-primary-obituary  mt-[35px]'>
              {mode === 'create' ? '방명록 등록하기' : '삭제'}
            </button>
          )}
        </div>
      </div>

      <EditModal
        isOpen={!!isOpenValidate}
        onClose={() => {
          setIsOpenValidate(undefined);
        }}
        onSubmit={(password) => {
          onValidatePassword(password);
        }}
      />

      <DeleteModal
        isOpen={isOpenDelete}
        onClose={() => {
          setIsOpenDelete(false);
        }}
        onSubmit={onDelete}
      />

      <SuccessModal
        id={id}
        isOpen={isOpenSuccess}
        onClose={() => {
          setIsOpenSuccess(false);
        }}
      />

      <CreateSuccessModal
        id={id}
        isOpen={isCreateSuccess}
        onClose={() => {
          setIsCreateSuccess(false);
        }}
      />
    </div>
  );
};

export default page;
