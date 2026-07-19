import { PROJECT_STATUS } from '@/utils/enums/project/project-list.enum';

export const mockProjectData = [
  {
    projectName: 'Alpha Project',
    requestDate: 1663082400000, // Example Unix timestamp (September 13, 2022)
    projectStatus: PROJECT_STATUS.EXPERT_MATCHING_IN_PROGRESS,
    meetingDate: 1663168800000, // Example Unix timestamp (September 14, 2022)
  },
  {
    projectName: 'Beta Project',
    projectStatus: PROJECT_STATUS.PROJECT_COMPLETED,
    requestDate: 1663946400000, // Example Unix timestamp (September 23, 2022)
    meetingDate: 1664032800000, // Example Unix timestamp (September 24, 2022)
  },
  {
    projectName: 'Gamma Project',
    projectStatus: PROJECT_STATUS.PROJECT_IN_PROGRESS,
    requestDate: 1664724000000, // Example Unix timestamp (October 2, 2022)
    meetingDate: 1664810400000, // Example Unix timestamp (October 3, 2022)
  },
  {
    projectName: 'Delta Project',
    projectStatus: PROJECT_STATUS.REQUEST_COMPLETED,
    requestDate: 1665415200000, // Example Unix timestamp (October 10, 2022)
    meetingDate: 1665501600000, // Example Unix timestamp (October 11, 2022)
  },
  {
    projectName: 'Epsilon Project',
    projectStatus: PROJECT_STATUS.UNDER_CONTRACT,
    requestDate: 1666099200000, // Example Unix timestamp (October 18, 2022)
    meetingDate: 1666185600000, // Example Unix timestamp (October 19, 2022)
  },
  {
    projectName: 'Alpha Project',
    requestDate: 1663082400000, // Example Unix timestamp (September 13, 2022)
    projectStatus: PROJECT_STATUS.EXPERT_MATCHING_IN_PROGRESS,
    meetingDate: 1663168800000, // Example Unix timestamp (September 14, 2022)
  },
  {
    projectName: 'Beta Project',
    projectStatus: PROJECT_STATUS.PROJECT_COMPLETED,
    requestDate: 1663946400000, // Example Unix timestamp (September 23, 2022)
    meetingDate: 1664032800000, // Example Unix timestamp (September 24, 2022)
  },
  {
    projectName: 'Gamma Project',
    projectStatus: PROJECT_STATUS.PROJECT_IN_PROGRESS,
    requestDate: 1664724000000, // Example Unix timestamp (October 2, 2022)
    meetingDate: 1664810400000, // Example Unix timestamp (October 3, 2022)
  },
  {
    projectName: 'Delta Project',
    projectStatus: PROJECT_STATUS.REQUEST_COMPLETED,
    requestDate: 1665415200000, // Example Unix timestamp (October 10, 2022)
    meetingDate: 1665501600000, // Example Unix timestamp (October 11, 2022)
  },
  {
    projectName: 'Epsilon Project',
    projectStatus: PROJECT_STATUS.UNDER_CONTRACT,
    requestDate: 1666099200000, // Example Unix timestamp (October 18, 2022)
    meetingDate: 1666185600000, // Example Unix timestamp (October 19, 2022)
  },
];

export const mockProjectExpertData = [
  {
    projectName: 'Alpha Project',
    projectStatus: PROJECT_STATUS.EXPERT_MATCHING_IN_PROGRESS,
    consultantDate: 1663082400000, // Example Unix timestamp (September 13, 2022)
    scheduleConfirmedDate: 1663168800000, // Example Unix timestamp (September 14, 2022)
  },
  {
    projectName: 'Beta Project',
    projectStatus: PROJECT_STATUS.PROJECT_COMPLETED,
    consultantDate: 1663946400000, // Example Unix timestamp (September 23, 2022)
    scheduleConfirmedDate: 1664032800000, // Example Unix timestamp (September 24, 2022)
  },
  {
    projectName: 'Gamma Project',
    projectStatus: PROJECT_STATUS.PROJECT_IN_PROGRESS,
    consultantDate: 1664724000000, // Example Unix timestamp (October 2, 2022)
    scheduleConfirmedDate: 1664810400000, // Example Unix timestamp (October 3, 2022)
  },
  {
    projectName: 'Delta Project',
    projectStatus: PROJECT_STATUS.REQUEST_COMPLETED,
    consultantDate: 1665415200000, // Example Unix timestamp (October 10, 2022)
    scheduleConfirmedDate: 1665501600000, // Example Unix timestamp (October 11, 2022)
  },
  {
    projectName: 'Epsilon Project',
    projectStatus: PROJECT_STATUS.UNDER_CONTRACT,
    consultantDate: 1666099200000, // Example Unix timestamp (October 18, 2022)
    scheduleConfirmedDate: 1666185600000, // Example Unix timestamp (October 19, 2022)
  },
];
