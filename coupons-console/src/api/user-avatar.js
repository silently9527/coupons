import config from '@/utils/config'

const api = {
  avatarUrl: '/file/avatar'
}

export function getAvatarUrl (avatarName) {
  return config.getServerUrl() + api.avatarUrl + '/' + avatarName
}
