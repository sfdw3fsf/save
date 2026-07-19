import { ObituaryDTO } from '@/store/obituary/dto/obituary.dto';
import { getObituaryMessagesApi, obituaryMessagesKeys } from '@/store/obituary/obituary.api';
import { obituaryMessagesRespToDto } from '@/store/obituary/obituary.service';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import FallingLeaves from '../../../../public/images/obituary/falling-leaves.png';

type MessageProps = {
  data?: ObituaryDTO;
  id: string;
};

const Message = ({ data, id }: MessageProps) => {
  const { data: obituaryMessagesData } = useQuery({
    queryKey: obituaryMessagesKeys.list({ id }),
    queryFn: () => getObituaryMessagesApi(id),
    select: (response) => {
      return response.data.data.map(obituaryMessagesRespToDto);
    },
    placeholderData: keepPreviousData,
    enabled: !!id,
  });

  return (
    <div className='mt-[90px] flex flex-col items-center mb-[128px] relative  '>
      <div className='bg-eff-base' style={{ backgroundImage: `url(${FallingLeaves.src})` }}></div>
      <div className='text-[16px] font-semibold'>기억의 숲, 방명록</div>
      <div className='text-[20px] font-bold'>조문글을 남겨 주세요</div>

      <div className='flex relative justify-center mt-[30px] mb-[15px] w-full'>
        <div className='text-[20px] font-semibold text-green-main'>
          {`For. `}
          <span className='font-extrabold text-[27px]'>{data?.petInformation.name}</span>
        </div>

        <a
          href={`card-owner/guest-list?id=${id}`}
          className='btn !text-black-nero text-[12px] !px-[6px] !py-[1px] !h-[30px] absolute right-0 top-2/4 -translate-y-2/4 hover:!text-white'>
          전체보기 &gt;
        </a>
      </div>

      <div className='max-h-[800px] overflow-auto w-full'>
        {obituaryMessagesData?.map((item) => (
          <div
            key={item.id}
            className='mb-4 flex flex-col border border-gray-400 px-5 pt-5 pb-[15px] w-full rounded-lg text-[15px] text-gray-400'>
            <div className='flex justify-between items-center'>
              <span>
                From.
                <span className='text-black-nero font-bold'>{item.author}</span>
              </span>
              <span className='text-[12px]'>{item.createdAt}</span>
            </div>
            <div className='mt-[7px]'>{item.content}</div>
            <a href={`card-owner/guest-list/edit?id=${id}&messageId=${item.id}`} className='self-end'>
              더보기
            </a>
          </div>
        ))}
      </div>

      <a href={`card-owner/guest-list/create?id=${id}`} className='btn-primary-obituary mt-[35px]'>
        방명록 작성하기
      </a>
    </div>
  );
};

export default Message;
